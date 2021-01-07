package com.example.hypermarket.fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.utilities.QueryPreferences;
import com.squareup.picasso.Picasso;


import static androidx.core.content.ContextCompat.startActivity;
import com.example.hypermarket.R;
import com.example.hypermarket.activity.ProductDetailActivity;
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
    private static Context mContext;
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
        String query = QueryPreferences.getPrefSearchQuery(getContext());
        if (query != null){
            mProductRepository.fetchSearchItemsAsync(query, new ProductRepository.Callbacks() {
                @Override
                public void onItemResponse(List<Product> items) {
                    mItems = items;
                    setupAdapter(mItems);
                }
            });
        }
        else{
            mProductRepository.fetchLatestItemsAsync(new ProductRepository.Callbacks() {
                @Override
                public void onItemResponse(List<Product> items) {
                    mItems = items;
                    setupAdapter(mItems);
                }
            });
        }
        setHasOptionsMenu(true);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        findViews(view);
        initViews();
        //setupAdapter(mItems);
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
                mProductRepository.fetchSearchItemsAsync(s, new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                        setupAdapter(mItems);
                    }
                });
                QueryPreferences.setPrefSearchQuery(getContext(), s);
                QueryPreferences.setIsSearch(getContext(), true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s){
                return false;
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
                        setupAdapter(mItems);
                    }
                });
                return true;

                case R.id.item_best_ratings:
                    mProductRepository.fetchItemsRatingAsync(new ProductRepository.Callbacks() {
                        @Override
                        public void onItemResponse(List<Product> items) {
                            mItems = items;
                            setupAdapter(mItems);
                        }
                    });
                    return true;

            case R.id.item_recents:
                mProductRepository.fetchLatestItemsAsync(new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                        setupAdapter(mItems);
                    }
                });
                return true;
            /*case R.id.menu_search_item:

                mProductRepository.fetchSearchItemsAsync(query, new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                        setupAdapter(mItems);
                    }
                });
                return true;*/
            case R.id.item_clear:
                QueryPreferences.setPrefSearchQuery(getContext(), null);
                mProductRepository.fetchItemsAsync(new ProductRepository.Callbacks() {
                    @Override
                    public void onItemResponse(List<Product> items) {
                        mItems = items;
                        setupAdapter(mItems);
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

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private ImageView productImage;
        private Button addToCart;

        public static final String TAG = "tag";
        private ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.btn_price);
        }

        public void bind(Product item) {
            productName.setText(item.getName());
            productPrice.setText(item.getPrice());
            Picasso.get()
                    .load(item.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(productImage);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, item.getUrl());
                    Intent intent = ProductDetailActivity.newIntent(mContext, item.getUrl());
                    mContext.startActivity(intent);
                }
            });
        }

        public static ProductViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item, parent, false);
            return new ProductViewHolder(view);
        }
    }

}