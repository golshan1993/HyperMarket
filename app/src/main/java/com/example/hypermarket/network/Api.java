package com.example.hypermarket.network;

import com.example.finalproject.model.Attribute;
import com.example.finalproject.model.Category;
import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Order;
import com.example.finalproject.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("products")
    Call<List<Product>> getAllProducts(@Query("orderby") String type, @Query("per_page") String perpage);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") String productId);

    @GET("products/categories/?per_page=100")
    Call<List<Category>> getAllCategories();

    @GET("products")
    Call<List<Product>> getProductsByCategory(@Query("category") String categoryId, @Query("orderby") String orderBy);

    @GET("products")
    Call<List<Product>> getSearchedProducts(@Query("search") String search, @Query("attribute") String attribute,
                                            @Query("attribute_term") String terms, @Query("orderby") String orderBy,
                                            @Query("order") String order, @Query("page") String page,
                                            @Query("per_page") int perPage);
    @GET("products/attributes")
    Call<List<Attribute>> getAttributes();

    @GET("products/attributes/{id}/terms")
    Call<List<Attribute.Term>> getTerms(@Path("id") String id);

    @POST("customers")
    Call<Customer> registerCustomer(@Body Customer customer);

    @GET("customers")
    Call<List<Customer>> getCustomer(@Query("email") String email);

    @POST("orders")
    Call<Order> sendOrder(@Body Order order);
}
