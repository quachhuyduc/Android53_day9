package com.example.android53_day8;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DummyService {


    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("products/{idProduct}")
    Call<Product> getProductById(@Path("idProduct") String idProduct);

    @GET("products/seatch")
    Call<ProductResponse> searchByName(@Query("q") String productName);

    @Headers("Content-Type: application/json")
    @POST("products/add")
    Call<JsonObject> addProduct(@Body AddProductRequest productRequest);

    @Headers("Content-Type: application/json")
    @PUT("products/{idProduct}")
    Call<Product> updateProduct(@Body AddProductRequest updateProduct);


}
