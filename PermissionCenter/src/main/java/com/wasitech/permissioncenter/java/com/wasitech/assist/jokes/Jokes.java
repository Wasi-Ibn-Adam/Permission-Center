package com.wasitech.permissioncenter.java.com.wasitech.assist.jokes;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.wasitech.basics.classes.Issue;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Jokes {
    private final OkHttpClient client = new OkHttpClient();

    public Jokes(Context context) {
        client.newCall(new RandomJokes().Client()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (call.isExecuted()) {
                    if (response.isSuccessful()) {
                        try {
                            Joke joke=RandomJokes.Notify(new JSONObject(response.body().string()));
                            joke.talk(context);
                        } catch (JSONException e) {
                            Issue.print(e, Jokes.class.getName());
                        }
                    }
                }
            }
        });
    }

/**
    {
        "error":false, "category":"Pun",
        "type":
            "twopart",
        "setup":
            "Did you hear about the cheese factory that exploded in France?",
        "delivery":
            "There was nothing but de brie.",
    "flags":{
        "nsfw":false, "religious":false, "political":false, "racist":false, "sexist":
        false, "explicit":false
    },"id":65, "safe":true, "lang":"en"
    }

    {
        "error":false, "category":"Pun", "type":"single", "joke":
        "I have these weird muscle spasms in my gluteus maximus.\nI figured out from my doctor that everything was alright:\nHe said \"Weird flex, butt okay.\"", "flags":
        {
            "nsfw":false, "religious":false, "political":false, "racist":false, "sexist":
            false, "explicit":false
        },"id":82, "safe":false, "lang":"en"
    }

    {
        "error":false, "category":"Pun", "type":"twopart", "setup":
        "No matter how kind you are...", "delivery":"German kids are always Kinder.", "flags":{
        "nsfw":false, "religious":false, "political":false, "racist":false, "sexist":
        false, "explicit":false
    },"safe":true, "id":278, "lang":"en"
    }

    {
        "error":false, "category":"Pun", "type":"twopart", "setup":
        "A grocery store cashier asked if I would like my milk in a bag.", "delivery":
        "I told her \"No, thanks. The carton works fine\".", "flags":{
        "nsfw":false, "religious":false, "political":false, "racist":false, "sexist":
        false, "explicit":false
    },"id":171, "safe":true, "lang":"en"
    }

    {
        "error":false, "category":"Pun", "type":"twopart", "setup":
        "The gas Argon walks into a bar.\nThe barkeeper says \"What would you like to drink?\"", "delivery":
        "But Argon doesn't react.", "flags":{
        "nsfw":false, "religious":false, "political":false, "racist":false, "sexist":
        false, "explicit":false
    },"id":148, "safe":true, "lang":"en"
    }

*/
}