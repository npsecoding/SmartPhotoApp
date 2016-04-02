package com.example.nancy.smartphoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class POIView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poiview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        double latitude = getIntent().getDoubleExtra("mark_lat", 0);
        double longitude = getIntent().getDoubleExtra("mark_long", 0);
        LatLng loc = new LatLng(latitude, longitude);
        Toast.makeText(POIView.this, "lat: " + loc.longitude + " long: " + loc.longitude, Toast.LENGTH_SHORT).show();

        String weather = "Sunny with a Temperature of 25 and slight wind";
        String lighting = "Soft and Warm";
        String future = "March 17th 4:00pm" ;
        POI poi = new  POI(weather, lighting, future, loc);

        TextView weathertv = (TextView)findViewById(R.id.info_text_1);
        weathertv.setText("Weather: \n \n");
        weathertv.append(poi.getWeather());

        TextView lightingtv = (TextView)findViewById(R.id.info_text_2);
        lightingtv.setText("Lighting: \n \n");
        lightingtv.append(poi.getLighting());

        TextView futuretv = (TextView)findViewById(R.id.info_text_3);
        futuretv.setText("Future: \n \n");
        futuretv.append(poi.getFuture());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Settings on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SettingsView.class));
                break;
            default:
                break;
        }

        return true;
    }

}
