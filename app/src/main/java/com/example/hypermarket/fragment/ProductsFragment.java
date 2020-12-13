package com.example.hypermarket.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.ProductAdapter;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductRepository mProductRepository;
    private List<Product> mItems = new ArrayList<>();
    private String query;
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
            }
        });
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_hyper_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_selected);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search_item);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                query = s;
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_best_popularity:
                mProductRepository.fetchItemsPopularAsync(new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                    }
                });
                return true;

                case R.id.item_best_ratings:
                    mProductRepository.fetchItemsRatingAsync(new ProductRepository.Callbacks() {
                        @Override
                        public void onItemResponse(List<Product> items) {
                            mItems = items;
                        }
                    });
                    return true;

            case R.id.item_recents:
                mProductRepository.fetchItemsAsync(new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                    }
                });
                return true;
            case R.id.menu_search_item:

                mProductRepository.fetchSearchItemsAsync(query, new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_products);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void setupAdapter(List<Product> items) {
        /*Product product = new Product("Cake", "1000", "");
        items.add(product);*/
        ProductAdapter adapter = new ProductAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }
}