package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.AddressAdapter;
import com.example.finalproject.adapter.OrderProductAdapter;
import com.example.finalproject.database.CustomerAddressModel;
import com.example.finalproject.databinding.FragmentBasketOrderBinding;
import com.example.finalproject.viewModel.OrderViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketOrderFragment extends Fragment {

    private FragmentBasketOrderBinding mBinding;
    private OrderViewModel mViewModel;

    private OrderProductAdapter orderProductAdapter;
    private AddressAdapter addressAdapter;

    private CustomerAddressModel customerAddressModel;

    public static BasketOrderFragment newInstance() {
        Bundle args = new Bundle();
        BasketOrderFragment fragment = new BasketOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BasketOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket_order, container, false);

        setAddressRecyclerView();
        setProductsRecyclerView();
        setObservers();
        setListeners();

        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.addNewAddress.setOnClickListener(view -> getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, AddEditAddressFragment.newInstance())
                .addToBackStack(null)
                .commit());

        mBinding.sendOrderBtn.setOnClickListener(view -> sendOrder());
    }

    private void setAddressRecyclerView() {
        RecyclerView addressRecyclerView = mBinding.addressRecyclerView;
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        addressAdapter = new AddressAdapter((AppCompatActivity) getActivity());
        addressRecyclerView.setAdapter(addressAdapter);
    }

    private void setProductsRecyclerView() {
        RecyclerView shippingRecyclerView = mBinding.shippingRecyclerView;
        shippingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        orderProductAdapter = new OrderProductAdapter((AppCompatActivity) getActivity());
        shippingRecyclerView.setAdapter(orderProductAdapter);
    }

    private void setObservers() {
        mViewModel.getCustomerAddressList().observe(this, customerAddress ->
                addressAdapter.setAddressList(customerAddress));

        mViewModel.getBasketProducts().observe(this, basketProducts ->
                orderProductAdapter.setProducts(basketProducts));
    }

    private void sendOrder() {
        mViewModel.sendOrder(addressAdapter.getSelectedAddress()).observe(this, order -> {
            if (order != null) {
                Toast.makeText(getContext(), order.getId().toString(), Toast.LENGTH_SHORT).show();
                mViewModel.clearBasket();
                getActivity().onBackPressed();
            }
        });
    }
}
