package com.example.hypermarket.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.hypermarket.view.fragment.ProductListFragment;


public class ProductListActivity extends SingleFragmentActivity {

    public static final String SEARCH_TEXT_EXTRA = "searchTextExtra";
    public static final String SEARCHABLE_BOOLEAN_EXTRA = "searchableBooleanExtra";

    private String searchText = "" ;
    private Boolean searchable ;

    public static Intent newIntent(Context context , String searchText , Boolean searchable ){

        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(SEARCH_TEXT_EXTRA , searchText);
        intent.putExtra(SEARCHABLE_BOOLEAN_EXTRA , searchable);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ProductListFragment.newInstance(searchText , searchable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        searchText = getIntent().getStringExtra(SEARCH_TEXT_EXTRA);
        searchable = getIntent().getBooleanExtra(SEARCHABLE_BOOLEAN_EXTRA , false);
        super.onCreate(savedInstanceState);
    }
}
