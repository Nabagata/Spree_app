package com.spree.spree_app;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by A on 3/8/2016.
 */
public interface SpreeApiEndpointInterface {

    @POST("accounts/signup_mobile")
    Call<String> signUpMobile(@Body SignupRetrofitClass user);

}
