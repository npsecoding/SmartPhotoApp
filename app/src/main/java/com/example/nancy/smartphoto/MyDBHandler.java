package com.example.nancy.smartphoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

public class MyDBHandler extends SQLiteOpenHelper  implements Serializable{
    //Must update version number whenever we make changes to database schema
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "photo_locationDB.db";
    public static final String TABLE_NAME = "photo_locations";
    public static final String COL_ID = "_id";
    public static final String COL_LAT = "pl_latitude";
    public static final String COL_LON = "pl_longitude";
    public static final String COL_LOGTIME = "pl_logtime";
    public static final String COL_CITY = "pl_city";
    public static final String COL_TIMESTAMP_DAY1 = "pl_timestamp_d1";
    public static final String COL_TIMESTAMP_DAY2 = "pl_timestamp_d2";
    public static final String COL_TIMESTAMP_DAY3 = "pl_timestamp_d3";
    public static final String COL_TEMP_DAY1 = "pl_temp_d1";
    public static final String COL_TEMP_DAY2 = "pl_temp_d2";
    public static final String COL_TEMP_DAY3 = "pl_temp_d3";
    public static final String COL_CLOUDS_DAY1 = "pl_clouds_d1";
    public static final String COL_CLOUDS_DAY2 = "pl_clouds_d2";
    public static final String COL_CLOUDS_DAY3 = "pl_clouds_d3";


    //DB Constructor: We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_LAT + " REAL, " +
                COL_LON + " REAL, " +
                COL_LOGTIME + " INTEGER, " +
                COL_CITY + " TEXT, " +
                COL_TIMESTAMP_DAY1 + " INTEGER, " +
                COL_TIMESTAMP_DAY2 + " INTEGER, " +
                COL_TIMESTAMP_DAY3 + " INTEGER, " +
                COL_TEMP_DAY1 + " REAL, " +
                COL_TEMP_DAY2 + " REAL, " +
                COL_TEMP_DAY3 + " REAL, " +
                COL_CLOUDS_DAY1 + " TEXT, " +
                COL_CLOUDS_DAY2 + " TEXT, " +
                COL_CLOUDS_DAY3 + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add a new row to the database
    public void addEntry(PhotoLocation pl){
        ContentValues values = new ContentValues();
        values.put(COL_LAT, pl.get_latitude());
        values.put(COL_LON, pl.get_longitude());
        values.put(COL_LOGTIME, pl.get_time_of_log());
        values.put(COL_CITY, pl.get_city());
        values.put(COL_TIMESTAMP_DAY1, pl.get_timestamp_day1());
        values.put(COL_TIMESTAMP_DAY2, pl.get_timestamp_day2());
        values.put(COL_TIMESTAMP_DAY3, pl.get_timestamp_day3());
        values.put(COL_TEMP_DAY1, pl.get_temp_day1());
        values.put(COL_TEMP_DAY2, pl.get_temp_day2());
        values.put(COL_TEMP_DAY3, pl.get_temp_day3());
        values.put(COL_CLOUDS_DAY1, pl.get_clouds_day1());
        values.put(COL_CLOUDS_DAY2, pl.get_clouds_day2());
        values.put(COL_CLOUDS_DAY3, pl.get_clouds_day3());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Update a row in the database
    public void updateEntry(PhotoLocation pl){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        //String selection = "pl_latitude" + " = " + pl.get_latitude()+" AND "+"pl_longitude"+" = "+pl.get_longitude();

        //values.put(COL_LAT, pl.get_latitude());
        //values.put(COL_LON, pl.get_longitude());
        //values.put(COL_LOGTIME, pl.get_time_of_log());
        values.put(COL_CITY, pl.get_city());
        values.put(COL_TIMESTAMP_DAY1, pl.get_timestamp_day1());
        values.put(COL_TIMESTAMP_DAY2, pl.get_timestamp_day2());
        values.put(COL_TIMESTAMP_DAY3, pl.get_timestamp_day3());
        values.put(COL_TEMP_DAY1, pl.get_temp_day1());
        values.put(COL_TEMP_DAY2, pl.get_temp_day2());
        values.put(COL_TEMP_DAY3, pl.get_temp_day3());
        values.put(COL_CLOUDS_DAY1, pl.get_clouds_day1());
        values.put(COL_CLOUDS_DAY2, pl.get_clouds_day2());
        values.put(COL_CLOUDS_DAY3, pl.get_clouds_day3());

        double lats = pl.get_latitude();
        double longs = pl.get_longitude();
        String[] args = {String.valueOf(lats), String.valueOf(longs)};

        Log.i("HELlo world", args[0]);

        // db.update(TABLE_NAME, values, COL_LAT " = "  AND pl_longitude=?", null);
        db.update(TABLE_NAME, values, "pl_latitude=? AND pl_longitude=?", args);
        //db.update(TABLE_NAME, values, null, null);

        //db.close();
    }

    //Delete a product from the database
    public void deleteEntry(double lat){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL_LAT + "=\"" + lat + "\";");
    }


    // this function currently only will print the lat and long
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        //query translation: "select all columns from table and select all rows
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if( (c.getDouble(c.getColumnIndex("pl_latitude")) != -1) &&
                    (c.getDouble(c.getColumnIndex("pl_longitude")) != -1)) {
                dbString += "Latitude: ";
                dbString += String.valueOf(c.getDouble(c.getColumnIndex("pl_latitude")));
                dbString += "\tLongitude: ";
                dbString += String.valueOf(c.getDouble(c.getColumnIndex("pl_longitude")));
                dbString += "\tCity: ";
                dbString += String.valueOf(c.getString(c.getColumnIndex("pl_city")));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public Cursor getInformation(MyDBHandler mdbh){
        SQLiteDatabase SQ = mdbh.getReadableDatabase();
        String[] columns = {COL_ID, COL_LAT, COL_LON, COL_LOGTIME, COL_CITY, COL_TIMESTAMP_DAY1,
                            COL_TIMESTAMP_DAY2, COL_TIMESTAMP_DAY3, COL_TEMP_DAY1, COL_TEMP_DAY2,
                            COL_TEMP_DAY3, COL_CLOUDS_DAY1, COL_CLOUDS_DAY2, COL_CLOUDS_DAY3};
        Cursor cursor = SQ.query(TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getRow(MyDBHandler mdbh, double laty, double longy ){
        SQLiteDatabase SQ = mdbh.getReadableDatabase();

        String[] args = {String.valueOf(laty), String.valueOf(longy)};

        // db.update(TABLE_NAME, values, COL_LAT " = "  AND pl_longitude=?", null);

        Cursor cursor = SQ.query(TABLE_NAME, null, "pl_latitude=? AND pl_longitude=?", args, null, null, null);
        return cursor;
    }
}
