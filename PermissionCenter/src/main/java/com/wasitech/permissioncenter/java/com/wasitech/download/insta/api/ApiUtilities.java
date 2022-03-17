package com.wasitech.permissioncenter.java.com.wasitech.download.insta.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {
    public static Retrofit retrofit=null;
    public static InstaInterface getInterface(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(InstaInterface.class);
    }
}
