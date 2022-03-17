package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;


import com.wasitech.basics.classes.Format;
import com.wasitech.assist.classes.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;

public class AerisWeather {
    // 100 per day
    private Request Client(String url){
        return new Request.Builder()
                .url("https://aerisweather1.p.rapidapi.com/"+url)
                .get()
                .addHeader("x-rapidapi-key", "cb2f99e156msh479e4741f08f8d8p1e6111jsne65cd93111fc")
                .addHeader("x-rapidapi-host", "aerisweather1.p.rapidapi.com")
                .build();
    }

    public Request SevereAlerts(double lat,double lon){
        String url="alerts/"+lat+","+lon;
        return Client(url);
    }
    public Request Forecast(double lat,double lon) {
        String url="forecast/"+lat+","+lon;
        return Client(url);
    }
    public Request Curr(double lat,double lon) {
        String url="observations/"+lat+","+lon;
        return Client(url);
    }
    public Weather Notify(JSONObject obj) throws JSONException {
        JSONObject response = obj.getJSONObject("response");
        JSONObject loc = response.getJSONObject("loc");
        JSONObject place = response.getJSONObject("place");
        JSONObject result = response.getJSONObject("ob");
        JSONObject pro = response.getJSONObject("profile");
        String city=place.getString("city");
        if(!city.equals("")) {
            if(city.contains("("))
            city= Format.titleCase(city.substring(0,city.indexOf("(")));
        }
        return new Weather(
                pro.getString("tz"),
                result.getLong("sunrise"),
                result.getLong("sunset"),
                result.getDouble("windSpeedKPH"),
                result.getInt("tempC"),
                result.getInt("feelslikeC"),
                result.getInt("humidity"),
                result.getInt("sky"),
                result.getInt("pressureMB"),
                result.getString("weatherPrimary"),
                result.getString("weather"),
                city,
                result.getInt("tempC"),
                result.getInt("dewpointC"),
                loc.getDouble("lat"),
                loc.getDouble("long")
        );
            /*
        {
            "success":true,
                "error":null,
                "response":{
                    "id":"OPLA",
                    "dataSource":"METAR_NOAA",
                    "loc":{
                        "long":74.4,
                        "lat":31.516666666667
                    },
                    "place":{
                        "name":"lahore (civ\/mil)",
                        "city":"lahore (civ\/mil)",
                        "state":"", "country":"pk"
                    },
                    "profile":{
                        "tz":"Asia\/Karachi",
                        "tzname":"PKT",
                        "tzoffset":18000,
                        "isDST":false,
                        "elevM":217,
                        "elevFT":712
                    },
                    "obTimestamp":1626238500,
                    "obDateTime":"2021-07-14T09:55:00+05:00",
                    "ob":{
                        "type":"station",
                        "timestamp":1626238500,
                        "dateTimeISO":"2021-07-14T09:55:00+05:00",
                        "recTimestamp":1626238870,
                        "recDateTimeISO":"2021-07-14T10:01:10+05:00",
                        "tempC":28,
                        "tempF":82,
                        "dewpointC":24,
                        "dewpointF":75,
                        "humidity":79,
                        "pressureMB":1004,
                        "pressureIN":29.64,
                        "spressureMB":979,
                        "spressureIN":28.92,
                        "altimeterMB":1005,
                        "altimeterIN":29.68,
                        "windKTS":9,
                        "windKPH":17,
                        "windMPH":10,
                        "windSpeedKTS":9,
                        "windSpeedKPH":17,
                        "windSpeedMPH":10,
                        "windDirDEG":330,
                        "windDir":"NNW",
                        "windGustKTS":null,
                        "windGustKPH":null,
                        "windGustMPH":null,
                        "flightRule":"IFR",
                        "visibilityKM":3,
                        "visibilityMI":1.86,
                        "weather":"Mostly Cloudy with Smoke",
                        "weatherShort":"Smoke",
                        "weatherCoded":"::K,::BK",
                        "weatherPrimary":"Smoke",
                        "weatherPrimaryCoded":"::K",
                        "cloudsCoded":"BK",
                        "icon":"smoke.png",
                        "heatindexC":31.7,
                        "heatindexF":89,
                        "windchillC":27.8,
                        "windchillF":82,
                        "feelslikeC":31.7,
                        "feelslikeF":89,
                        "isDay":true,
                        "sunrise":1626221256,
                        "sunriseISO":"2021-07-14T 05:07:36+05:00",
                        "sunset":1626271739,
                        "sunsetISO":"2021-07-14T 19:08:59+05:00",
                        "snowDepthCM":null,
                        "snowDepthIN":null,
                        "precipMM":null,
                        "precipIN":null,
                        "solradWM2":514,
                        "solradMethod":"estimated",
                        "ceilingFT":10000,
                        "ceilingM":3048,
                        "light":59,
                        "uvi":null,
                        "QC":"O",
                        "QCcode":10,
                        "trustFactor":100,
                        "sky":81
                    },
                    "raw":"METAR OPLA 140455Z 33009KT 3000 FU SCT040 BKN100 28\/24 Q1005 TEMPO 32020G45KT 2000 TSRA FEW030CB RMK QFE978 RMK A29.70",
                    "relativeTo": {
                        "lat":31.61053513,
                        "long":74.34069333,
                        "bearing":152,
                        "bearingENG":"SSE",
                        "distanceKM":11.854,
                        "distanceMI":7.366
                    }
                }
        }
*/

    }





}
