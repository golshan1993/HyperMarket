package com.example.hypermarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.hypermarket.R;

public class ProductDetailFragment extends Fragment {

    public static final String USER_WEB_PAGE_ADDRESS = "userWebPageAddress";
    private WebView mWebView;
    private String webPageSelected;
    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(String webAddress) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(USER_WEB_PAGE_ADDRESS, webAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            webPageSelected = getArguments().getString(USER_WEB_PAGE_ADDRESS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mWebView = view.findViewById(R.id.web_view_product);
        mWebView.loadUrl(webPageSelected);
        return view;
    }
}