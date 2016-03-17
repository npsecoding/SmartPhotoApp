package com.example.nancy.smartphoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/16/2016.
 */
public class WeatherData {
    public List<Weather> weatherdata;

    public WeatherData(){
        weatherdata = new ArrayList<Weather>();
    }

    public void add(Weather weather){
        weatherdata.add(weather);
    }

    public void remove(Weather weather){
        weatherdata.remove(weather);
    }

}
