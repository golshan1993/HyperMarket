package com.example.hypermarket.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.activity.ProductDetailActivity;
import com.example.hypermarket.model.Product;
import com.squareup.picasso.Picasso;

import static androidx.core.content.ContextCompat.startActivity;


//import static androidx.core.content.ContextCompat.startActivity;


public class ProductViewHolder extends RecyclerView.ViewHolder {
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
                Intent intent = ProductDetailActivity.newIntent(view.getContext(), item.getUrl());
                //startActivity(intent);
            }
        });
    }

    public ProductViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }
}