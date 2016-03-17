package com.example.nancy.smartphoto;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings mapUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        ld.add(new Location(49.3017049, -123.1417003, "Stanley Park")); //Stanley Park
        wd.add(new Weather(25, "Sunny"));
        ld.add(new Location(49.2268144, -123.0004924, "Movie Theatre"));
        wd.add(new Weather(15, "Cloudy"));
        ld.add(new Location(49.2606052, -123.2459939, "UBC"));
        wd.add(new Weather(11, "Rainy"));
        ld.add(new Location(49.1748301,-123.1540775, "Skating Oval"));
        wd.add(new Weather(-4, "Cold"));

        for(int i = 0; i < ld.locationdata.size(); i++){

            Location loc = ld.locationdata.get(i);
            Weather weather = wd.weatherdata.get(i);

            // Add a marker in Stanley Park and move the camera
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

        LatLng loc_ubc = new LatLng(49.2606052, -123.2459939);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc_ubc));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}

