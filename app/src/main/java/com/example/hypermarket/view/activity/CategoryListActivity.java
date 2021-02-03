package com.example.hypermarket.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.hypermarket.view.fragment.CategoryListFragment;

public class CategoryListActivity extends SingleFragmentActivity {

    public static final String CATEGORY_ID_EXTRA = "categoryIdExtra";
    private int currentCategoryId ;

    public static Intent newIntent(Context context , int categoryId){

        Intent intent = new Intent(context, CategoryListActivity.class);
        intent.putExtra(CATEGORY_ID_EXTRA , categoryId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return CategoryListFragment.newInstance(currentCategoryId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentCategoryId = getIntent().getIntExtra(CATEGORY_ID_EXTRA ,0 );
        super.onCreate(savedInstanceState);
    }
}
