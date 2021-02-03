package com.example.hypermarket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {
    @Expose
    @SerializedName("state")
    private String state;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("postcode")
    private String postcode;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("address_2")
    private String address_2;
    @Expose
    @SerializedName("address_1")
    private String address_1;
    @Expose
    @SerializedName("company")
    private String company;
    @Expose
    @SerializedName("last_name")
    private String last_name;
    @Expose
    @SerializedName("first_name")
    private String first_name;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
