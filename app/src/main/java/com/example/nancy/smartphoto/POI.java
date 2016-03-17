package com.example.nancy.smartphoto;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class POI {

    private String weather;
    private String future;
    private String lighting;
    private LatLng loc;

    public POI(String weather, String lighting, String future, LatLng loc) {
        this.weather = weather;
        this.future = future;
        this.lighting = lighting;
        this.loc = loc;
    }

    public String getWeather() {
        return weather;
    }

    public void setContent(String content) {
        this.weather = weather;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting = lighting;
    }

    public LatLng getLoc() {
        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }
}
