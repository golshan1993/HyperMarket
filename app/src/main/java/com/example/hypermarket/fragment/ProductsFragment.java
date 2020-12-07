package com.example.hypermarket.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.ProductAdapter;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.repository.ProductRepository;
import com.example.hypermarket.retrofit.model.CommerceResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductRepository mProductRepository;
    private List<Product> mItems = new ArrayList<>();
    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductRepository = new ProductRepository();
        mProductRepository.fetchItemsAsync(new ProductRepository.Callbacks() {
            @Override
            public void onItemResponse(List<Product> items) {
                mItems = items;
                setupAdapter(items);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        findViews(view);
        initViews();
        setupAdapter(mItems);
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_products);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void setupAdapter(List<Product> items) {
        ProductAdapter adapter = new ProductAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }
}