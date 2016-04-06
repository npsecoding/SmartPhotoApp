package com.example.nancy.smartphoto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

public class POIView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poiview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Point Information");
        setSupportActionBar(toolbar);

        double latitude = getIntent().getDoubleExtra("mark_lat", 0);
        double longitude = getIntent().getDoubleExtra("mark_long", 0);

        Cursor cursor = SplashActivity.dbHandler.getRow(SplashActivity.dbHandler, latitude, longitude);


        String date1 = "";
        String temp1 = "";
        String clouds1 = "" ;

        String date2 = "";
        String temp2 = "";
        String clouds2 = "" ;

        String date3 = "";
        String temp3 = "";
        String clouds3 = "" ;

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");

        if(!cursor.moveToFirst()){
            Toast.makeText(getApplicationContext(), "Nothing found in DB in POIview", Toast.LENGTH_SHORT).show();
        }
        else {
            //For each entry in the database, build location and weather objects
            do {
                date1 = sdf1.format(new Date(1000 * (cursor.getLong(5))));
                date2 = sdf2.format(new Date(1000*(cursor.getLong(6))));
                date3 = sdf3.format(new Date(1000*(cursor.getLong(7))));

                temp1 = String.valueOf(cursor.getDouble(8));
                temp2 = String.valueOf(cursor.getDouble(9));
                temp3 = String.valueOf(cursor.getDouble(10));

                clouds1 = cursor.getString(11);
                clouds2 = cursor.getString(12);
                clouds3 = cursor.getString(13);
            } while (cursor.moveToNext());
        }



        TextView weathertv = (TextView)findViewById(R.id.info_text_1);

        StringBuilder today = new StringBuilder();
        today.append("\tDate: ");
        today.append(date1);
        today.append("\n Temp: ");
        today.append(temp1+ " C");
        today.append("\n Cloud: ");
        today.append(clouds1);

        StringBuilder tom = new StringBuilder();
        tom.append("\tDate: ");
        tom.append(date2);
        tom.append("\n Temp: ");
        tom .append(temp2 + " C");
        tom.append("\n Cloud: ");
        tom.append(clouds2);

        StringBuilder nextd = new StringBuilder();
        nextd.append("\tDate: ");
        nextd.append(date3);
        nextd.append("\n Temp: ");
        nextd.append(temp3+ " C");
        nextd.append("\n Cloud: ");
        nextd.append(clouds3);

        weathertv.setText("Today: \n");
        weathertv.append(today.toString());

        TextView lightingtv = (TextView)findViewById(R.id.info_text_2);
        lightingtv.setText("Tomorrow: \n");
        lightingtv.append(tom.toString());

        TextView futuretv = (TextView)findViewById(R.id.info_text_3);
        futuretv.setText("In 2 Days: \n");
        futuretv.append(nextd.toString());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
