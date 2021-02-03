package com.example.hypermarket.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.databinding.ItemProductMainListBinding;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.view.fragment.ProductDetailFragment;
import com.example.hypermarket.viewModel.ProductDetailFragmentViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductMainAdapter extends RecyclerView.Adapter<ProductMainAdapter.ProductHolder> {

    private List<Product> mProducts = new ArrayList<>();
    private AppCompatActivity mActivity ;

    public ProductMainAdapter(AppCompatActivity mActivity ) {
        this.mActivity = mActivity;
    }

    public ProductMainAdapter(AppCompatActivity mActivity , List<Product> productList) {
        this.mActivity = mActivity;
        this.mProducts = productList;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        ItemProductMainListBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(),
                R.layout.item_product_main_list, parent, false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.bind(product);
    }


    @Override
    public int getItemCount() {

        return mProducts == null ? 0 : mProducts.size();
    }



    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imageView ;
        private ProductDetailFragmentViewModel mViewModel ;
        private ItemProductMainListBinding mBinding ;
        private Product mProduct;

        public ProductHolder(@NonNull ItemProductMainListBinding binding) {
            super(binding.getRoot());
            mBinding = binding ;
            mViewModel = ViewModelProviders.of(mActivity).get(ProductDetailFragmentViewModel.class);
            imageView = mBinding.holderIvProductImage ;
        }


        public void bind(Product product) {

            mProduct = product ;
            mViewModel.getProduct().setValue(mProduct);
            mBinding.setProductDetailsViewModel(mViewModel);
            mBinding.executePendingBindings();

            itemView.setOnClickListener(view ->{
                mViewModel.getProduct().setValue(mProduct);
                    mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container , ProductDetailFragment.newInstance())
                    .addToBackStack("transaction")
                    .commit();
                    });

            Picasso.get().load(product.getImages().get(0).getSrc()).placeholder(R.drawable.alt).into(imageView);
        }
    }

}
