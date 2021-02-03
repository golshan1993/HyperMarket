package com.example.hypermarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.hypermarket.model.CartProduct;
import com.example.hypermarket.repositories.ProductRepository;

import java.util.List;

public class ProductBasketViewModel extends AndroidViewModel {

    private MutableLiveData<List<CartProduct>> cartProductBasketList;

    public ProductBasketViewModel(@NonNull Application application) {
        super(application);
        cartProductBasketList = ProductRepository.getInstance(application).getBasketProducts();
    }

    public MutableLiveData<List<CartProduct>> getCartProductBasketList() {
        return cartProductBasketList;
    }

    public String totalBasketPrice() {
        Long totalPrice = 0L;

        for (CartProduct cartProduct : cartProductBasketList.getValue()) {
            totalPrice += Long.valueOf(cartProduct.getPrice());
        }
        return String.valueOf(totalPrice);
    }
}
