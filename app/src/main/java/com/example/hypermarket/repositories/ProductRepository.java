package com.example.hypermarket.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.hypermarket.model.CartProduct;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.network.Api;
import com.example.hypermarket.network.RetrofitInstance;
import com.example.hypermarket.utils.Preferences;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private static ProductRepository mInstance;

    private Context mContext;

    private MutableLiveData<List<CartProduct>> mBasketProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mNewProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mRatedProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mVisitedProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mVipProducts = new MutableLiveData<>();

    private ProductRepository(Context context) {
        mContext = context;
    }

    public static ProductRepository getInstance(Context context) {
        if (mInstance == null)
            mInstance = new ProductRepository(context);

        return mInstance;
    }

    public void loadBasketProducts() {
        List<CartProduct> list = Preferences.getProductList(mContext);
        mBasketProducts.postValue(list);
    }

    public void saveBasketProducts() {
        Preferences.setProductList(mContext, mBasketProducts.getValue());
    }

    public MutableLiveData<Product> getProductById(int id) {
        MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();
        RetrofitInstance.getRetrofit().create(Api.class).getProduct(String.valueOf(id)).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful())
                    productMutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        return productMutableLiveData;
    }


    public MutableLiveData<List<CartProduct>> getBasketProducts() {
        return mBasketProducts;
    }

    public void deleteCartproduct(CartProduct cartProduct) {
        List<CartProduct> list = mBasketProducts.getValue();
        list.remove(cartProduct);
        mBasketProducts.setValue(list);
        saveBasketProducts();
    }

    public MutableLiveData<List<Product>> getNewProducts() {
        return mNewProducts;
    }

    public MutableLiveData<List<Product>> getRatedProducts() {
        return mRatedProducts;
    }

    public MutableLiveData<List<Product>> getVisitedProducts() {
        return mVisitedProducts;
    }

    public MutableLiveData<List<Product>> getVipProducts() {
        return mVipProducts;
    }

    public void loadNewProductList() throws IOException {
        mNewProducts.postValue(RetrofitInstance.getRetrofit().create(Api.class)
                .getAllProducts("date", "10").execute().body());
    }

    public void loadRatedProductList() throws IOException {
        mRatedProducts.postValue(RetrofitInstance.getRetrofit().create(Api.class)
                .getAllProducts("rating", "10").execute().body());
    }

    public void loadVisitedProductList() throws IOException {
        mVisitedProducts.postValue(RetrofitInstance.getRetrofit().create(Api.class)
                .getAllProducts("popularity", "10").execute().body());
    }
}
