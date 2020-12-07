package com.example.hypermarket.repository;

import android.util.Log;

import com.example.hypermarket.model.Product;
import com.example.hypermarket.retrofit.NetworkParams;
import com.example.hypermarket.retrofit.RetrofitInstance;
import com.example.hypermarket.retrofit.WooCommerceService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class ProductRepository {
    private WooCommerceService mWooCommerceService;
    private static final String TAG = "ProductRepository";
    public ProductRepository(){
        Retrofit retrofit = RetrofitInstance.create();
        mWooCommerceService = retrofit.create(WooCommerceService.class);
    }

    public List<Product> fetchItems() {
        Call<List<Product>> call = mWooCommerceService.listItems(NetworkParams.PRODUCTS_VIEW);
        try {
            Response<List<Product>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    //this method can be run in any thread.
    public void fetchItemsAsync(Callbacks callBacks) {
        Call<List<Product>> call = mWooCommerceService.listItems(NetworkParams.PRODUCTS_VIEW);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchLatestItemsAsync(Callbacks callBacks) {
        Call<List<Product>> call = mWooCommerceService.listItems(NetworkParams.ORDER_BY_LATEST);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchItemsRatingAsync(Callbacks callBacks) {
        Call<List<Product>> call = mWooCommerceService.listItems(NetworkParams.ORDER_BY_RATING);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchItemsPopularAsync(Callbacks callBacks) {
        Call<List<Product>> call = mWooCommerceService.listItems(NetworkParams.ORDER_BY_POPULARITY);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public interface Callbacks {
        void onItemResponse(List<Product> items);
    }
}
