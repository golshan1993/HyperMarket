package com.example.hypermarket.repository;

import com.example.hypermarket.retrofit.NetworkParams;
import com.example.hypermarket.retrofit.RetrofitInstance;
import com.example.hypermarket.retrofit.WooCommerceService;
import com.example.hypermarket.retrofit.model.CategoriesItem;
import com.example.hypermarket.retrofit.model.CollectionItem;
import com.example.hypermarket.retrofit.model.CommerceResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
public class ProductRepository {
    private WooCommerceService mWooCommerceService;
    public ProductRepository(){
        Retrofit retrofit = RetrofitInstance.create();
        mWooCommerceService = retrofit.create(WooCommerceService.class);
    }

    public List<CommerceResponse> fetchAllItems(){
        Call<List<CommerceResponse>> call = mWooCommerceService.listItems(NetworkParams.PRODUCTS_VIEW);
        try{
            Response<List<CommerceResponse>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CommerceResponse> fetchPopularItems(){
        Call<List<CommerceResponse>> call = mWooCommerceService.listItems(NetworkParams.ORDER_BY_POPULARITY);
        try{
            Response<List<CommerceResponse>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CommerceResponse> fetchRatingItems(){
        Call<List<CommerceResponse>> call = mWooCommerceService.listItems(NetworkParams.ORDER_BY_RATING);
        try{
            Response<List<CommerceResponse>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CommerceResponse> fetchRecentItems(){
        Call<List<CommerceResponse>> call = mWooCommerceService.listItems(NetworkParams.ORDER_BY_LATEST);
        try{
            Response<List<CommerceResponse>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
