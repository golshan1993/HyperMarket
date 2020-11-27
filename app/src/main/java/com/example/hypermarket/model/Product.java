package com.example.hypermarket.model;

public class Product {
    private String name;
    private String price;
    private String mUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Product(){

    }

    public Product(String name, String price, String url) {
        this.name = name;
        this.price = price;
        mUrl = url;
    }
}
