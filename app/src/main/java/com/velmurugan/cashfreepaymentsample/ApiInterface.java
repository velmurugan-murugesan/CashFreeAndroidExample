package com.velmurugan.cashfreepaymentsample;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("cftoken/order")
    public Call<OrderTokenResponse> getOrderToken(@Header("x-client-id") String clientId, @Header("x-client-secret") String clientSecret
            , @Body OrderTokenRequest orderTokenRequest);



}
