package com.example.hypermarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hypermarket.model.CartProduct;
import com.example.hypermarket.model.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Preferences {

    private static final String PREF_NAME = "Digikala";

    private static final String PREF_PRODUCT_BASKET = "query";
    private static final String PREF_CUSTOMER = "customer";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static Customer getLoginedCustomer(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        if (prefs == null)
            return null;

        Customer customer = new Customer();
        Gson gson = new Gson();
        String jsonText =  prefs.getString(PREF_CUSTOMER, null) ;
        if(gson.fromJson(jsonText, Customer.class) != null) {
            customer = gson.fromJson(jsonText, Customer.class);
            return customer;
        }
        else
            return null ;

    }

    public static void setLoginedCustomer(Context context, Customer customer) {
        SharedPreferences prefs = getSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String jsonText = gson.toJson(customer);
        editor.putString(PREF_CUSTOMER, jsonText);
        editor.apply();
    }

    public static List<CartProduct> getProductList(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        if (prefs == null)
            return null;

        List<CartProduct> cartProductList = new ArrayList<>();
        Type type = new TypeToken<List<CartProduct>>() {}.getType();
        Gson gson = new Gson();
        String jsonText =  prefs.getString(PREF_PRODUCT_BASKET, null) ;
        if(gson.fromJson(jsonText , type) instanceof List)
            cartProductList = gson.fromJson(jsonText, type);

        return cartProductList;
    }

    public static void setProductList(Context context, List<CartProduct> cartProductList) {
        SharedPreferences prefs = getSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String jsonText = gson.toJson(cartProductList);
        editor.putString(PREF_PRODUCT_BASKET, jsonText);
        editor.apply();
    }
}

