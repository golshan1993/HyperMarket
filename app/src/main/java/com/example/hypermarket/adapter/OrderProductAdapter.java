package com.example.hypermarket.adapter;


import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.databinding.ItemOrderProductBinding;
import com.example.hypermarket.model.CartProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ProductHolder> {

    private List<CartProduct> mProducts = new ArrayList<>();
    private AppCompatActivity mActivity ;

    public OrderProductAdapter(AppCompatActivity mActivity ) {
        this.mActivity = mActivity;
    }

    public OrderProductAdapter(AppCompatActivity mActivity , List<CartProduct> productList) {
        this.mActivity = mActivity;
        this.mProducts = productList;
    }

    public void setProducts(List<CartProduct> products) {
        mProducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        ItemOrderProductBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(),
                R.layout.item_order_product, parent, false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        CartProduct product = mProducts.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imageView ;
        private ItemOrderProductBinding mBinding ;
        private CartProduct mProduct;

        public ProductHolder(@NonNull ItemOrderProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding ;
            imageView = mBinding.holderIvProductImage ;
        }

        public void bind(CartProduct product) {
            mProduct = product ;
            mBinding.holderTvProductName.setText(product.getName());
            mBinding.holderTvProductPrice.setText(product.getPrice());
            Picasso.get().load(product.getImages().get(0).getSrc()).placeholder(R.drawable.alt).into(imageView);
        }
    }
}

