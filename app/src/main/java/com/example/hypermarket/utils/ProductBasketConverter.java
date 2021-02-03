package com.example.hypermarket.utils;

import com.example.hypermarket.model.CartProduct;
import com.example.hypermarket.model.Product;

public class ProductBasketConverter {

    public static CartProduct convertToCartProduct(Product product) {
        return new CartProduct(product.getName(), product.getId(), product.getImages(), product.getPrice(), product.getDescription());
    }

    public static Product convertToProduct(CartProduct cartProduct) {
        Product product =  new Product();
        product.setName(cartProduct.getName());
        product.setImages(cartProduct.getImages());
        product.setDescription(cartProduct.getDescription());
        product.setPrice(cartProduct.getPrice());

        return product ;
    }

}
