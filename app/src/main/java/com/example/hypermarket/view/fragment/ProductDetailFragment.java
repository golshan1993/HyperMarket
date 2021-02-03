package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.ImageSliderAdapter;
import com.example.hypermarket.databinding.FragmentProductDetailBinding;
import com.example.hypermarket.utils.UiUtils;
import com.example.hypermarket.viewModel.ProductDetailFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    // public static final String PRODUCT_ID_ARG = "productIdArg";
    //private Product mProduct;

    private ProductDetailFragmentViewModel mViewModel;
    private FragmentProductDetailBinding mBinding;

    public static ProductDetailFragment newInstance() {
        Bundle args = new Bundle();
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductDetailFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        setObservers();
        setListeners();

        return mBinding.getRoot();
    }

    private void setDetail() {
        mBinding.setProductDetailsViewModel(mViewModel);
        mBinding.imageSlider.setSliderAdapter(new ImageSliderAdapter(getContext(), mViewModel.getProduct().getValue().getImages()));
    }

    private void setListeners(){
        mBinding.fragmentProductDetailAddToBasket.setOnClickListener(view12 -> {
            mViewModel.addProductToShoppingCart(mViewModel.getProduct().getValue());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProductBasketFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        mBinding.backDetailImageview.setOnClickListener(view1 -> getActivity().onBackPressed());
    }

    private void setObservers() {
        mViewModel.getProduct().observe(this, product -> {
            setDetail();
        });

        mViewModel.getShoppingCartList().observe(this, shoppingBagList -> {
            UiUtils.setBadgeicon(shoppingBagList.size(), mBinding.shoppingCartIconDetailFragment.basketTextview);
        });

    }
}
