package com.example.hypermarket.view.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.hypermarket.view.fragment.StartFragment;

public class StartActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return StartFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
