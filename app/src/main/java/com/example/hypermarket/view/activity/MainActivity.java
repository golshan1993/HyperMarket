package com.example.hypermarket.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.hypermarket.R;
import com.example.hypermarket.view.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    private MainFragment mainFragment ;

    public static Intent newIntent(Context context ){

        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment instanceof MainFragment)
            mainFragment = (MainFragment) fragment;

        if(mainFragment == null)
            super.onBackPressed();
        else if(!mainFragment.closeDrawer())
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
