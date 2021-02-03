package com.example.hypermarket.view.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hypermarket.R;
import com.example.hypermarket.databinding.FragmentStartBinding;
import com.example.hypermarket.model.CartProduct;
import com.example.hypermarket.repositories.CategoriesRepository;
import com.example.hypermarket.repositories.CustomerRepository;
import com.example.hypermarket.repositories.ProductRepository;
import com.example.hypermarket.view.activity.MainActivity;
import com.example.hypermarket.viewModel.MainFragmentViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private MaterialButton tryAgainButton;
    private ProgressBar progressBar;

    private FragmentStartBinding mBinding ;
    private MainFragmentViewModel mainFragmentViewModel ;

    public static StartFragment newInstance() {
        Bundle args = new Bundle();
        StartFragment fragment = new StartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_start, container, false);

        initUi();
        startInit();
        tryAgainButton.setOnClickListener(view1 -> startInit());
        return mBinding.getRoot();
    }

    private void onNetworkUnavailable() {
        tryAgainButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void startInit() {
        if (isNetworkAvailable()) {
            tryAgainButton.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            new InitProductsAsynceTask().execute();
        } else
            onNetworkUnavailable();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void initUi() {
        tryAgainButton = mBinding.tryagainButton;
        progressBar = mBinding.progressBar2;
        List<CartProduct> list = new ArrayList<>();
        ProductRepository.getInstance(getContext()).getBasketProducts().setValue(list);
    }

    private class InitProductsAsynceTask extends AsyncTask<Void, String, Void> {

        private Boolean result = true;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mainFragmentViewModel.loadAttributesFromApi();
                mainFragmentViewModel.loadCategoriesListFromApi();
                mainFragmentViewModel.loadNewProductListFromApi();
                mainFragmentViewModel.loadRatedProductListFromApi();
                mainFragmentViewModel.loadVisitedProductListFromApi();
                mainFragmentViewModel.loadAttributeTermsFromApi();

            } catch (IOException e) {
                publishProgress("خطا در دریافت اطلاعات از دیجی کالا");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String toastString = values[0];
            Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
            result = false;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(result) {
                startActivity(MainActivity.newIntent(getActivity()));
                CategoriesRepository.getInstance(getContext()).generateParentList();
                ProductRepository.getInstance(getContext()).getVipProducts().setValue(ProductRepository.getInstance(getContext())
                        .getRatedProducts().getValue()
                        .subList(0, 4));
                CustomerRepository.getInstance(getContext()).loadLoginnedCustomer();
                getActivity().finish();
            }
            else
                onNetworkUnavailable();
        }
    }
}
