package com.wasitech.permissioncenter.java.com.wasitech.download.insta.api;

import com.wasitech.download.insta.InstaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InstaInterface {
    @GET("video")
    Call<InstaModel> getInfo(
            @Query("link") String link
    );
}
