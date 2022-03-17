package com.wasitech.permissioncenter.java.com.wasitech.assist.jokes;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;

public class RandomJokes {
    // 49,999 per day
    // FvHFgP1pAdLK
    public Request Client(){
        return new Request.Builder()
                .url("https://random-stuff-api.p.rapidapi.com/joke/pun?api_key=FvHFgP1pAdLK&lan=hindi")
                .get()
                .addHeader("x-api-key", "FvHFgP1pAdLK")
                .addHeader("x-rapidapi-key", "cb2f99e156msh479e4741f08f8d8p1e6111jsne65cd93111fc")
                .addHeader("x-rapidapi-host", "random-stuff-api.p.rapidapi.com")
                .build();
    }

    public static Joke Notify(JSONObject obj) throws JSONException {
        if(obj.getString("type").equals("twopart"))
            return new Joke(obj.getString("setup"), obj.getString("delivery"));
        else
            return new Joke(obj.getString("joke"));
    }

}
