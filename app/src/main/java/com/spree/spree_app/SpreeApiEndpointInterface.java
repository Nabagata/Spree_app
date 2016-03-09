package com.spree.spree_app;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by A on 3/8/2016.
 */
public interface SpreeApiEndpointInterface {

    @POST("accounts/signup_mobile")
    Call<String> signUpMobile(@Body SignupRetrofitClass user);

    @GET("events/fetch_min_max_event/{event_id}")
    Call<String> fetchMinMaxEvent(@Path("event_id") String event_id);

    @POST("events/registerteam_mobile")
    Call<String> registerTeam(@Body EventRegisterRetrofitClass registration);
}
