package com.example.hypermarket.model;

public class Product {
    private String name;
    private String price;
    private String mUrl;
    private String [] mImageUrl;
    private String [] mCategory;

    public String[] getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String[] imageUrl) {
        mImageUrl = imageUrl;
    }

    public String[] getCategory() {
        return mCategory;
    }

    public void setCategory(String[] category) {
        mCategory = category;
    }

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

    public Product(String name, String price, String url, String[] imageUrl, String[] category) {
        this.name = name;
        this.price = price;
        mUrl = url;
        mImageUrl = imageUrl;
        mCategory = category;
    }

}
