package com.example.hypermarket.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.hypermarket.model.Category;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.network.Api;
import com.example.hypermarket.network.RetrofitInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    private static CategoriesRepository mInstance;
    private Context mContext ;

    private MutableLiveData<List<Category>> mCategories = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mParentCategories = new MutableLiveData<>() ;
    private MutableLiveData<List<Product>> mNewProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mRatedProducts = new MutableLiveData<>();

    private CategoriesRepository(Context mContext) {
        this.mContext = mContext;
    }

    public static CategoriesRepository getInstance(Context context) {
        if (mInstance == null)
            mInstance = new CategoriesRepository(context);

        return mInstance;
    }

    public MutableLiveData<List<Category>> getAllCategories() {
        return mCategories;
    }

    public MutableLiveData<List<Category>> getParentCategories() {
        return mParentCategories;
    }

    public void loadCategoriesList() throws IOException {
        mCategories.postValue(RetrofitInstance.getRetrofit().create(Api.class)
                .getAllCategories().execute().body());
    }

    public void generateParentList() {
        List list = new ArrayList();
        for (Category category : mCategories.getValue()) {
            if (category.getParent() == 0)
                list.add(category);
        }
        mParentCategories.postValue(list);
    }

    public List<Category> getSubCategoires(long parentId) {
        List<Category> result = new ArrayList<>();
        for (Category category : mCategories.getValue()) {
            if (category.getParent() == parentId)
                result.add(category);
        }
        return result;
    }

    public Category getCategoryById(int id) {
        for (Category category : mCategories.getValue())
            if (category.getId() == id)
                return category;
        return null;
    }


    public MutableLiveData<List<Product>> getNewProducts() {
        return mNewProducts;
    }

    public MutableLiveData<List<Product>> getRatedProducts() {
        return mRatedProducts;
    }

    public void loadNewProductList(int categoryId) {
        RetrofitInstance.getRetrofit().create(Api.class)
                .getProductsByCategory(String.valueOf(categoryId), "date").enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful())
                            mNewProducts.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }

    public void loadRatedProductList(int categoryId) {
        RetrofitInstance.getRetrofit().create(Api.class)
                .getProductsByCategory(String.valueOf(categoryId), "rating").enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful())
                            mRatedProducts.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
    }
}
