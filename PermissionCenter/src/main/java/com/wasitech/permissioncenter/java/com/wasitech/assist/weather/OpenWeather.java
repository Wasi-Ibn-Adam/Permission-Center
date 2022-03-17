package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;

import com.wasitech.assist.classes.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import com.wasitech.basics.classes.Issue;
import okhttp3.Request;

public class OpenWeather {
    public static final String API_1 = "9747bbda21b099c0630d6a949722451c";
    public static final String API_2 = "2b006cafa0ba4e1237eb44084581597b";
    public static final String API_3 = "59c623a0987a238b46a3792dbc7414bf";
    public static final String API_4 = "fe3e6f4c34dd6ff95b941a2619467f03";

    private final String API;

    public OpenWeather(String API) {
        this.API = API;
    }

    public Request getRequest(double lat, double lon) {
        return new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly,minutely&appid=" + API)
                .get()
                .build();
    }

    public static Weather getCurrent(JSONObject obj) {
        try {
            JSONObject daily = obj.getJSONArray("daily").getJSONObject(0);
            JSONObject temp = daily.getJSONObject("temp");
            JSONObject curr = obj.getJSONObject("current");
            JSONObject weather = curr.getJSONArray("weather").getJSONObject(0);
            return new Weather(
                    obj.getString("timezone"),
                    curr.getLong("sunrise") * 1000,  // give seconds
                    curr.getLong("sunset") * 1000,
                    curr.getDouble("wind_speed"),
                    curr.getDouble("temp") - 273.15,
                    curr.getDouble("feels_like") - 273.15,
                    curr.getInt("humidity"),
                    curr.getInt("clouds"),
                    curr.getInt("pressure"),
                    weather.getString("main"),
                    weather.getString("description"),
                    "",
                    temp.getInt("max"),
                    temp.getInt("min"),
                    obj.getDouble("lat"),
                    obj.getDouble("lon")
            );
        } catch (JSONException e) {
            Issue.print(e,OpenWeather.class.getName());
        }
        return null;
    }

}
