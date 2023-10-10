package com.example.android53_day8;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CartService {

    @GET("carts")
    Call<CartResponse> getCarts();
}
