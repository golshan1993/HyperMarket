package com.example.hypermarket.activity;

import android.content.Context;
import android.content.Intent;


import androidx.fragment.app.Fragment;

import com.example.hypermarket.fragment.ProductDetailFragment;

public class ProductDetailActivity extends SingleFragmentActivity{

    public static final String WEB_PAGE_URL = "WebPageUrl";

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(WEB_PAGE_URL, url);

        return intent;
    }

    @Override
    public Fragment createFragment() {
        String url = getIntent().getStringExtra(WEB_PAGE_URL);
        return ProductDetailFragment.newInstance(url);
    }
}