package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;


import com.wasitech.assist.classes.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;

public class Community {
    // 500 per day
    private Request Client(String url) {
        return new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/"+url)
                .get()
                .addHeader("x-rapidapi-key", "cb2f99e156msh479e4741f08f8d8p1e6111jsne65cd93111fc")
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .build();

    }


    public Request SearchWeather(String city,double lat,double lon) {

        String url="find?q="+city+"&lon="+lon+"&type=link%2C%20accurate&lat="+lat;
        return Client(url);
    }
    public Request Curr(String city,double lat,double lon) {
        String url="weather?q="+city+"%2Cuk&lat="+lat+"&lon="+lon+"&mode=xml%2C%20html";
        return Client(url);
    }

    public Weather Notify(JSONObject obj) throws JSONException {
        JSONObject test = obj.getJSONObject("test");
        JSONObject cord = test.getJSONObject("coord");
        JSONObject result = test.getJSONArray("weather").getJSONObject(0);
        JSONObject main = test.getJSONObject("main");
        JSONObject cl = main.getJSONObject("clouds");
        JSONObject wind = main.getJSONObject("wind");
        JSONObject sys = main.getJSONObject("sys");
        return new com.wasitech.assist.classes.Weather(
                main.getString("name"),
                sys.getLong("sunrise"),
                sys.getLong("sunset"),
                wind.getDouble("speed"),
                main.getInt("temp")- 273.15,
                main.getInt("temp")- 273.15,
                main.getInt("humidity"),
                cl.getInt("all"),
                main.getInt("pressure"),
                result.getString("main"),
                result.getString("description"),
                "",
                (int)(main.getInt("temp_max")- 273.15),
                (int)(main.getInt("tempmin")- 273.15),
                cord.getDouble("lat"),
                cord.getDouble("lon")
        );

        /*
       test(
       {        "coord":
                    {"lon":-0.1257,"lat":51.5085},
                "weather":[
                    {
                        "id":804,
                        "main":"Clouds",
                        "description":"overcast clouds",
                        "icon":"04d"
                     }
                ],
                "base":"stations",
                "main":{
                    "temp":290.62,
                    "feelslike":290.56,
                    "tempmin":288.28,
                    "temp_max":292.56,
                    "pressure":1018,
                    "humidity":82},
                    "visibility":10000,
                    "wind":{
                        "speed":4.63,
                        "deg":310
                     },
                     "clouds":{
                        "all":90
                      },
                      "dt":1626250193,
                      "sys":{
                            "type":2,
                            "id":2006068,
                            "country":"GB",
                            "sunrise":1626235183,
                            "sunset":1626293556
                       },
                       "timezone":3600,
                       "id":2643743,
                       "name":"London",
                       "cod":200
                 }
             )
        * */


    }





}
