package com.wasitech.permissioncenter.java.com.wasitech.assist.weather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.classes.Weather;
import com.wasitech.assist.command.family.Coder;
import com.wasitech.assist.command.family.WFamily;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Weathers {
    private final OpenWeather ow1;
    private final OpenWeather ow2;
    private final OpenWeather ow3;
    private final OpenWeather ow4;
    private final AerisWeather w1;
    private int num = 0;
    private final OkHttpClient client = new OkHttpClient();
    private final Context context;
    private final OldNotificationMaker noti;
    private final LocalDB db;
    private final LocationManager manager;
    private int req;

    public Weathers(Context context) {
        this.context = context;
        noti = new OldNotificationMaker(context);
        db = new LocalDB(context);
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        ow1 = new OpenWeather(OpenWeather.API_1);
        ow2 = new OpenWeather(OpenWeather.API_2);
        ow3 = new OpenWeather(OpenWeather.API_3);
        ow4 = new OpenWeather(OpenWeather.API_4);
        w1 = new AerisWeather();
    }

    public void getCurr(boolean talk, int req) {
        this.req = req;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    manager.removeUpdates(this);
                    getCurr(location.getLatitude(), location.getLongitude(), talk);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    manager.removeUpdates(this);
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @NonNull
                @Override
                protected Object clone() throws CloneNotSupportedException {
                    return super.clone();
                }
            };
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                } catch (Exception e) {
                    Issue.print(e,Weathers.class.getName());
                }
            } else
                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public void getCurr(boolean talk, int req, String city) {
        this.req = req;
        String loc = cityLatLong(context, city);
        if (loc != null) {
            double lat = Double.parseDouble(loc.split(":")[0]);
            double lon = Double.parseDouble(loc.split(":")[1]);
            getCurr(lat, lon, talk);
        }
    }


    public static String cityName(Context context, double lon, double lat) {
        String city = "";
        Geocoder gcd = new Geocoder(context, Locale.ENGLISH);
        if (Geocoder.isPresent()) {
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(lat, lon, 1);
            } catch (IOException e) {
                Issue.print(e,Weathers.class.getName());
            }
            if (addresses != null && addresses.size() > 0) {
                city = addresses.get(0).getLocality();
            }
            return city;
        }
        return null;
    }

    public static String cityLatLong(Context context, String city) {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocationName(city, 1);
        } catch (IOException e) {
            Issue.print(e,Weathers.class.getName());
        }
        if (addresses != null && addresses.size() > 0) {
            return addresses.get(0).getLatitude() + ":" + addresses.get(0).getLongitude();
        }
        return null;
    }

    public boolean isLocationActive() {
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private Request getCurrRequest(double lat, double lon) {
        num++;
        switch (num) {
            case 1: {
                return ow1.getRequest(lat, lon);
            }
            case 2: {
                return ow2.getRequest(lat, lon);
            }
            case 3: {
                return ow3.getRequest(lat, lon);
            }
            case 4: {
                return ow4.getRequest(lat, lon);
            }
            default: {
                num = 0;
                return w1.Curr(lat, lon);
            }
        }
    }

    private void getCurr(double lat, double lon, boolean isTalk) {
        Request request = getCurrRequest(lat, lon);
        if (request != null) {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Issue.print(e,Weathers.class.getName());
                    getCurr(lat, lon, isTalk);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response r1) {
                    if (r1.isSuccessful()) {
                        try {
                            ResponseBody body = r1.body();
                            assert body != null;
                            JSONObject obj = new JSONObject(body.string());
                            if (call.isExecuted()) {
                                Weather weather = OpenWeather.getCurrent(obj);
                                if (weather == null)
                                    weather = w1.Notify(obj);
                                weather.setCity(WFamily.city + "");
                                try {
                                    if (isTalk)
                                        talk(weather);
                                    else
                                        saveAndNoti(weather);
                                } catch (Exception e) {
                                    Issue.print(e,Weathers.class.getName());
                                }
                            }
                        } catch (Exception e) {
                            Issue.print(e,Weathers.class.getName());
                        }
                    } else {
                        getCurr(lat, lon, isTalk);
                    }
                }
            });
        }
    }

    private void saveAndNoti(Weather weather) {
        if (weather.getClouds() == -1) throw new RuntimeException("weather empty");
        else {
            db.addWeather(weather);
            noti.Weather(weather);
        }
    }

    private void talk(Weather weather) {
        if (weather.getClouds() == -1) throw new RuntimeException("weather empty");
        else {
            db.addWeather(weather);
            StringBuilder builder = new StringBuilder();
            switch (req) {
                case Coder.R_WEATHER_N:
                case Coder.R_WEATHER_L_N: {
                    builder.append("Its ").append(weather.getTalk()).append(", ");
                }
                case Coder.R_WEATHER_T:
                case Coder.R_WEATHER_L_T: {
                    builder.append("Now Temperature is ").append(weather.getTemp())
                            .append(" celsius ");
                    if (weather.getCity() != null && !weather.getCity().equals(""))
                        builder.append(" in ").append(weather.getCity());
                    else
                        builder.append(".");
                    break;
                }
                default: {
                    noti.Weather(weather);
                }
            }
            noti.Weather(weather);
            ProcessApp.talk(context, builder.toString());
        }
    }

}
