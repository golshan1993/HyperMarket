package com.example.hypermarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.model.Product;
import com.example.hypermarket.viewholder.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private Context mContext;
    private List<Product> mItems;
    private ImageView mImageViewItem;
    public List<Product> getItems() {
        return mItems;
    }

    public void setItems(List<Product> items) {
        mItems = items;
    }

    public ProductAdapter(List<Product> items) {
        mItems = items;
    }

    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ProductViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product current = mItems.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

