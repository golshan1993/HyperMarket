package com.example.hypermarket.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.hypermarket.R;

public class ProductDetailFragment extends Fragment {

    public static final String USER_WEB_PAGE_ADDRESS = "userWebPageAddress";
    private WebView mWebView;
    private String webPageSelected;
    private ProgressBar mProgressBar;
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
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return(false);
            }
        });
        mWebView.loadUrl(webPageSelected);
        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100){
                    mProgressBar.setVisibility(View.GONE);
                }
                else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
            public void onReceivedTitle(WebView view, String title){
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(title);
            }
        });
        return view;
    }
}