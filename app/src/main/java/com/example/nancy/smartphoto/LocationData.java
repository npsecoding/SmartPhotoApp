package com.example.nancy.smartphoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/16/2016.
 */
public class LocationData {
    public List<Location> locationdata;

    public LocationData(){
        locationdata = new ArrayList<Location>();
    }

    public void add(Location loc){
        locationdata.add(loc);
    }

    public void remove(Location loc){
        locationdata.remove(loc);
    }
}