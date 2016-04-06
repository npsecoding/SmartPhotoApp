package com.example.nancy.smartphoto;

import java.text.SimpleDateFormat;
import java.util.Date;

//Each row in the database can be represented by an object
//Columns will represent the objects properties
public class PhotoLocation {

    //Invalid constants for initializing objects
    private final long LONG_NOT_SET = -1000;
    private final double DOUBLE_NOT_SET = -1000;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    private int _id;
    private String _city;
    private double _latitude;
    private double _longitude;
    private long _time_of_log;
    private long _timestamp_day1;
    private long _timestamp_day2;
    private long _timestamp_day3;
    private double _temp_day1;
    private double _temp_day2;
    private double _temp_day3;
    private String _clouds_day1;
    private String _clouds_day2;
    private String _clouds_day3;



    public long get_time_of_log() {
        return _time_of_log;
    }

    public void set_time_of_log(long _time_of_log) {
        this._time_of_log = _time_of_log;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public double get_temp_day1() {
        return _temp_day1;
    }

    public void set_temp_day1(double _temp_day1) {
        this._temp_day1 = _temp_day1;
    }

    public double get_temp_day3() {
        return _temp_day3;
    }

    public void set_temp_day3(double _temp_day3) {
        this._temp_day3 = _temp_day3;
    }

    public String get_clouds_day1() {
        return _clouds_day1;
    }

    public void set_clouds_day1(String _clouds_day1) {
        this._clouds_day1 = _clouds_day1;
    }

    public String get_clouds_day2() {
        return _clouds_day2;
    }

    public void set_clouds_day2(String _clouds_day2) {
        this._clouds_day2 = _clouds_day2;
    }

    public String get_clouds_day3() {
        return _clouds_day3;
    }

    public void set_clouds_day3(String _clouds_day3) {
        this._clouds_day3 = _clouds_day3;
    }

    public long get_timestamp_day1() {
        return _timestamp_day1;
    }

    public String getStringDate(long timestamp_x){
        return sdf.format(new Date(1000*timestamp_x));
    }

    public void set_timestamp_day1(long _timestamp_day1) {
        this._timestamp_day1 = _timestamp_day1;
    }

    public long get_timestamp_day2() {
        return _timestamp_day2;
    }

    public void set_timestamp_day2(long _timestamp_day2) {
        this._timestamp_day2 = _timestamp_day2;
    }

    public long get_timestamp_day3() {
        return _timestamp_day3;
    }

    public void set_timestamp_day3(long _timestamp_day3) {
        this._timestamp_day3 = _timestamp_day3;
    }



    public double get_temp_day2() {
        return _temp_day2;
    }

    public void set_temp_day2(double _temp_day2) {
        this._temp_day2 = _temp_day2;
    }




    // PhotoLocation Constructors
    public PhotoLocation(){
        this._city = "NOT SET";
        this._latitude = DOUBLE_NOT_SET;
        this._longitude = DOUBLE_NOT_SET;
        this._time_of_log = LONG_NOT_SET;
        this._timestamp_day1 = LONG_NOT_SET;
        this._timestamp_day2 = LONG_NOT_SET;
        this._timestamp_day3 = LONG_NOT_SET;
        this._temp_day1 = DOUBLE_NOT_SET;
        this._temp_day2 = DOUBLE_NOT_SET;
        this._temp_day3 = DOUBLE_NOT_SET;
        this._clouds_day1 = "NOT SET";
        this._clouds_day2 = "NOT SET";
        this._clouds_day3 = "NOT SET";
    }

    public PhotoLocation(double lat, double lon, long time_of_log){
        this._latitude = lat;
        this._longitude = lon;
        this._time_of_log = time_of_log;

        this._city = "NOT SET";
        this._timestamp_day1 = LONG_NOT_SET;
        this._timestamp_day2 = LONG_NOT_SET;
        this._timestamp_day3 = LONG_NOT_SET;
        this._temp_day1 = DOUBLE_NOT_SET;
        this._temp_day2 = DOUBLE_NOT_SET;
        this._temp_day3 = DOUBLE_NOT_SET;
        this._clouds_day1 = "NOT SET";
        this._clouds_day2 = "NOT SET";
        this._clouds_day3 = "NOT SET";
    }


    public String get_city() {
        return _city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public double get_latitude() {
        return this._latitude;
    }
    public double get_longitude() {
        return this._longitude;
    }

}