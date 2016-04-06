package com.example.nancy.smartphoto;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapView extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings mapUiSettings;
    private LatLng clickedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set title of bar to Map of Points
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Map of Points");

        setContentView(R.layout.activity_map_view);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        double latitude = getIntent().getDoubleExtra("card_lat", 0);
        double longitude = getIntent().getDoubleExtra("card_long", 0);
        clickedMarker = new LatLng(latitude, longitude);

        //Toast.makeText(MapView.this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();
        //Toast.makeText(MapView.this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Set up controls for map
        mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setCompassEnabled(false);
        mapUiSettings.setScrollGesturesEnabled(true);
        mapUiSettings.setZoomGesturesEnabled(true);
        mapUiSettings.setTiltGesturesEnabled(false);
        mapUiSettings.setRotateGesturesEnabled(false);
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setCompassEnabled(false);
        mapUiSettings.setMapToolbarEnabled(true);

        //List of locations and their weather
        LocationData ld = new LocationData();
        WeatherData wd = new WeatherData();

        int count = 0;
        Cursor cursor = SplashActivity.dbHandler.getInformation(SplashActivity.dbHandler);
        if(!cursor.moveToFirst()){
            Toast.makeText(getApplicationContext(), "Nothing found in DB during Weather Fetch.", Toast.LENGTH_SHORT).show();
        }
        else {
            //For each entry in the database, build location and weather objects
            do {
                double latitude = cursor.getDouble(1);
                double longitude = cursor.getDouble(2);
                String city_name = cursor.getString(4);
                double todays_temp = cursor.getDouble(8);
                String todays_clouds = cursor.getString(11);
                ld.add(new Location(latitude, longitude, city_name));
                wd.add(new Weather(todays_temp, todays_clouds));
                count++;
                Log.i("JBHSBJHBSJ", "SJNKJSNS");
            } while (cursor.moveToNext());
        }

        for(int i = 0; i < count; i++){

            Location loc = ld.locationdata.get(i);
            Weather weather = wd.weatherdata.get(i);

            // Add a markers onto the map
            LatLng loc_mark = new LatLng( loc.getLat(), loc.getLong());
            mMap.addMarker(new MarkerOptions()
                    .position(loc_mark)
                    .title(loc.getName())
                    .snippet("Weather: " + weather.getCond() + "\n" + "Temperature :" + weather.getTemp()));
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext();

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                title.setTextSize(14);

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());
                snippet.setTextSize(12);

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        //Transition to the POIView when a marker is clicked
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapView.this, POIView.class);

                //Pass the locatoin to the POI
                intent.putExtra("mark_lat", marker.getPosition().latitude);
                intent.putExtra("mark_long", marker.getPosition().longitude);
                //intent.putExtra("MyDBHandler", dbHandler);

                startActivity(intent);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(clickedMarker));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
    }
}

