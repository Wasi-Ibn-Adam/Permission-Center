package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;

import okhttp3.Request;

public class ClimaCell {
    // 500 per day

    private Request Client(String url) {
        return new Request.Builder()
                .url("https://climacell-microweather-v1.p.rapidapi.com/"+url)
                .get()
                .addHeader("x-rapidapi-key", "cb2f99e156msh479e4741f08f8d8p1e6111jsne65cd93111fc")
                .addHeader("x-rapidapi-host", "climacell-microweather-v1.p.rapidapi.com")
                .build();

    }

    public Request Curr(double lat,double lon) {
        String  url="weather/realtime?lat="+lat+"&lon="+lon;
        return Client(url);
    }
    public Request Forecast(double lat,double lon) {
        String  url="weather/forecast/hourly?lon="+lon+"&lat="+lat;
        return Client(url);
    }

}
