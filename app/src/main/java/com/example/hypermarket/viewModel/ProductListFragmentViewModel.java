package com.example.hypermarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hypermarket.model.Attribute;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.network.Api;
import com.example.hypermarket.network.RetrofitInstance;
import com.example.hypermarket.repositories.FilterRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mProductList = new MutableLiveData<>() ;
    private MutableLiveData<List<Attribute.Term>> mSelectedTerms ;
    private MutableLiveData<List<Attribute>> mAttributes ;
    private FilterRepository mFilterRepository ;

    public ProductListFragmentViewModel(@NonNull Application application) {
        super(application);
        mSelectedTerms = FilterRepository.getInstance(application).getSelectedTerms() ;
        mFilterRepository = FilterRepository.getInstance(application);
        mAttributes = mFilterRepository.getAttributes();
    }


    public MutableLiveData<List<Attribute>> getmAttributes() {
        return mAttributes;
    }

    public MutableLiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public MutableLiveData<List<Attribute.Term>> getSelectedTerms() {
        return mSelectedTerms;
    }

    public void setEmptySelectedTerm(){
       mFilterRepository.getSelectedTerms().setValue(new ArrayList<>());
    }

    public void loadFilteredListFromApi(){
        loadFilteredListFromApi(null , null , null , 1);
    }

    public void loadFilteredListFromApi(String searchText , String orderBy, String orderType , int page){
        String filter = "";
        String attribute = "";
        if(mSelectedTerms.getValue().size() != 0) {
            attribute = mSelectedTerms.getValue().get(0).getAttributeSlug();
            for (Attribute.Term term : mSelectedTerms.getValue()) {
                filter += term.getId() + ",";
            }
        }

        RetrofitInstance.getRetrofit().create(Api.class).
                getSearchedProducts(searchText, attribute, filter , orderBy , orderType , String.valueOf(page) , 20)
                .enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful())
                    mProductList.postValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

}
