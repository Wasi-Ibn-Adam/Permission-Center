package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;


import okhttp3.Request;

public class WeatherBit {
    // 125 per day
    private Request Client(String url) {
        return new Request.Builder()
                .url("https://weatherbit-v1-mashape.p.rapidapi.com/"+url)
                .get()
                .addHeader("x-rapidapi-key", "cb2f99e156msh479e4741f08f8d8p1e6111jsne65cd93111fc")
                .addHeader("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
                .build();
    }

    public Request SevereAlerts(double lat,double lon) {
        String url="alerts?lat="+lat+"&lon="+lon;
        return Client(url);
    }
    public Request Forecast120Hour(double lat,double lon) {
        String url="forecast/hourly?lat="+lat+"&lon="+lon+"&hours=48";
        return Client(url);
    }
    public Request Forecast16Day(double lat,double lon) {
        String url="forecast/daily?lat="+lat+"&lon="+lon;
        return Client(url);
    }
    public Request ForecastHour(double lat,double lon) {
        String url="forecast/minutely?lat="+lat+"&lon="+lon;
        return Client(url);
    }
    public Request Curr(double lat,double lon) {
        String url="current?lat="+lat+"&lon="+lon;
        return Client(url);
    }






}
