package com.example.nancy.smartphoto;

/**
 * Created by Administrator on 3/16/2016.
 */
public class Weather {

    private double temp;
    private String cond;
    private String future;

    public Weather(double temp, String cond, String future){
        this.temp = temp;
        this.cond = cond;
        this.future = future;
    }

    public Weather(double temp, String cond){
        this.temp = temp;
        this.cond = cond;
    }

    public double getTemp(){
        return this.temp;
    }

    public String getCond(){
        return this.cond;
    }

    public void setFuture(String future){
        this.future = future;
    }

    public String getFuture(){
        return this.future;
    }
}
