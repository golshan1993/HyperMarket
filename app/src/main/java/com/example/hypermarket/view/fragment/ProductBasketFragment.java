package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.ProductBasketAdapter;
import com.example.hypermarket.databinding.FragmentProductBasketBinding;
import com.example.hypermarket.repositories.CustomerRepository;
import com.example.hypermarket.utils.UiUtils;
import com.example.hypermarket.viewModel.ProductBasketViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductBasketFragment extends Fragment {

    private RecyclerView basketRecyclerView;
    private ProductBasketAdapter productBasketAdapter;

    private ProductBasketViewModel mViewModel;
    private FragmentProductBasketBinding mBinding;

    public static ProductBasketFragment newInstance() {
        Bundle args = new Bundle();
        ProductBasketFragment fragment = new ProductBasketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductBasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductBasketViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_basket, container, false);

        initUi();
        setupBadge();
        setShoppingCartRecyclerView();
        setListeners();

        mViewModel.getCartProductBasketList().observe(this, shoppingBagList -> {
            mBinding.sumPriceShoppingCartTextView.setText(mViewModel.totalBasketPrice());
            if (shoppingBagList.size() == 0) {
                mBinding.nullMassageShoppingBag.setVisibility(View.VISIBLE);
                mBinding.shoppingCartFinalTextView.setVisibility(View.INVISIBLE);
                basketRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mBinding.nullMassageShoppingBag.setVisibility(View.GONE);
                basketRecyclerView.setVisibility(View.VISIBLE);
                mBinding.shoppingCartFinalTextView.setVisibility(View.VISIBLE);
                productBasketAdapter.setProducts(shoppingBagList);
                productBasketAdapter.notifyDataSetChanged();
            }
        });

        return mBinding.getRoot();
    }

    private void initUi() {
        basketRecyclerView = mBinding.shoppingCartRecyclerView;
    }

    private void setShoppingCartRecyclerView() {
        productBasketAdapter = new ProductBasketAdapter((AppCompatActivity) getActivity());
        basketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        basketRecyclerView.setAdapter(productBasketAdapter);
    }

    private void setListeners() {
        mBinding.shoppingCartCloseImageview.setOnClickListener(view1 -> getActivity().onBackPressed());

        mBinding.shoppingCartFinalTextView.setOnClickListener(view -> {
            if (CustomerRepository.getInstance(getContext()).getCustomer().getValue() == null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
            else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, BasketOrderFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setupBadge() {
        mViewModel.getCartProductBasketList().observe(this, shoppingBagList -> {
            UiUtils.setBadgeicon(shoppingBagList.size(), mBinding.shoppingCartIconShoppinFragment.basketTextview);
        });
    }
}
