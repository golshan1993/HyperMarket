package com.example.hypermarket.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.hypermarket.fragment.ProductsFragment;

public class MainActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return ProductsFragment.newInstance();
    }
}