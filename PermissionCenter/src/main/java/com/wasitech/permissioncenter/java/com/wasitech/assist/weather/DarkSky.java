package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;


import java.util.Date;

import okhttp3.Request;

public class DarkSky {
    // 100 per month
    private Request Client(String url) {
        return new Request.Builder()
                .url("https://dark-sky.p.rapidapi.com/"+url)
                .get()
                .addHeader("x-rapidapi-key", "cb2f99e156msh479e4741f08f8d8p1e6111jsne65cd93111fc")
                .addHeader("x-rapidapi-host", "dark-sky.p.rapidapi.com")
                .build();
    }

    public Request Curr(double lat,double lon) {
        String url=lat+","+lon+","+new Date();
        return Client(url);
    }
    public Request Forecast(double lat,double lon) {
        String url=lat+","+lon+"?lang=en&units=auto";
        return Client(url);
    }







}
