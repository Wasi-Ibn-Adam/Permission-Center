package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import com.wasitech.assist.command.family.QA;

import java.text.DecimalFormat;
import java.util.Date;

public class Weather {
    // 500 per month
    private final String timeZone;
    private final long sunRise;
    private final long sunSet;
    private final double windSpeed;
    private final double temp;
    private final double feels;
    private final int humidity;
    private final int clouds;
    private final int pressure;
    private final String main;
    private final String talk;
    private String city;
    private final int max, min;
    private final double lat, lon;

    public Weather(String timeZone, long sunRise, long sunSet, double windSpeed, double temp, double feels, int humidity, int clouds, int pressure, String main, String talk, String city, int max, int min, double lat, double lon) {
        this.timeZone = timeZone;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.windSpeed = windSpeed;
        this.temp = temp;
        this.feels = feels;
        this.humidity = humidity;
        this.clouds = clouds;
        this.pressure = pressure;
        this.main = main;
        this.talk = talk;
        this.city = city;
        this.max = max;
        this.min = min;
        this.lat = lat;
        this.lon = lon;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "timeZone='" + timeZone + '\'' +
                ", sunRise=" + sunRise +
                ", sunSet=" + sunSet +
                ", windSpeed=" + windSpeed +
                ", temp=" + temp +
                ", humidity=" + humidity +
                ", clouds=" + clouds +
                ", pressure=" + pressure +
                ", main='" + main + '\'' +
                ", talk='" + talk + '\'' +
                '}';
    }

    public int getFeels() {
        return (int) feels;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public long getSunRise() {
        return sunRise;
    }

    public long getSunSet() {
        return sunSet;
    }

    public String getSunSetTime() {
        return QA.toStringDate(new Date(sunSet));
    }

    public String getSunRiseTime() {
        return QA.toStringDate(new Date(sunRise));
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getTemp() {
        return (int) temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public double getPressure() {
        String pre = new DecimalFormat("#.##").format(pressure / 33.86);
        return Double.parseDouble(pre);
    }

    public String getMain() {
        return main;
    }

    public String getTalk() {
        return talk;
    }
}
