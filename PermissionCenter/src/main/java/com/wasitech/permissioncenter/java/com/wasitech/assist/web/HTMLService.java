package com.wasitech.permissioncenter.java.com.wasitech.assist.web;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.wasitech.basics.classes.Issue;

public class HTMLService extends AsyncTask<String, String, String> {

    public static String getHtmlAsString(String sUrl) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(sUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Issue.print(e, HTMLService.class.getName());
        }

        return sb.toString();
    }

    @Override
    protected String doInBackground(String... strings) {
        return getHtmlAsString(strings[0]);
    }
}