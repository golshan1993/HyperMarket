package com.example.hypermarket.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.model.Product;
import com.squareup.picasso.Picasso;


public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private ImageView productImage;
        private Button addToCart;

        private ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.btn_price);
        }

        public void bind(Product item) {
            productName.setText(item.getName());
            productPrice.setText(item.getPrice());
            Picasso.get()
                    .load(item.getImageUrl()[0])
                    .placeholder(R.drawable.ic_launcher_lock)
                    .into(productImage);
        }

        public static ProductViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item, parent, false);
            return new ProductViewHolder(view);
        }
}
