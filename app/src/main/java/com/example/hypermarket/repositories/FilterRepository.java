package com.example.hypermarket.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.hypermarket.model.Attribute;
import com.example.hypermarket.network.Api;
import com.example.hypermarket.network.RetrofitInstance;

import java.io.IOException;
import java.util.List;

public class FilterRepository {

    private static FilterRepository mInstance;
    private Context mContext ;

    private MutableLiveData<List<Attribute>> mAttributes = new MutableLiveData<>() ;
    private MutableLiveData<List<Attribute.Term>> mSelectedTerms = new MutableLiveData<>();

    private FilterRepository(Context mContext) {
        this.mContext = mContext;
    }

    public static FilterRepository getInstance(Context context) {
        if (mInstance == null)
            mInstance = new FilterRepository(context);

        return mInstance;
    }

    public MutableLiveData<List<Attribute>> getAttributes() {
        return mAttributes;
    }

    public void loadAttributesFromApi () throws IOException {
        mAttributes.postValue(RetrofitInstance.getRetrofit().create(Api.class)
                .getAttributes().execute().body());
    }

    public void loadAttributeTerms() throws IOException {
        for (Attribute attribute : mAttributes.getValue()) {
            attribute.setTerms(RetrofitInstance.getRetrofit().create(Api.class)
                    .getTerms(String.valueOf(attribute.getId())).execute().body());
        }
    }

    public MutableLiveData<List<Attribute.Term>> getSelectedTerms() {
        return mSelectedTerms;
    }

    public void addSelectedTerm(Attribute.Term term) {
        List list = mSelectedTerms.getValue();
        list.add(term);
        mSelectedTerms.postValue(list);
    }

    public void removeSelectedTerm(Attribute.Term term) {
        List list = mSelectedTerms.getValue();
        list.remove(term);
        mSelectedTerms.postValue(list);
    }

}
