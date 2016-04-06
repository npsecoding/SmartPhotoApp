package com.example.nancy.smartphoto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public class SettingsView extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String TAG1 = "SettingsOnCreate";
    private final String TAG2 = "PostExecute";

    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;

    //Brian's  OpenWeatherMap Key -- please obtain your own from their website!
    private static final String APP_ID = "3258eed2676aa5ced3181124419ef99b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set title of bar to Settings
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Settings");

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        Log.i(TAG1, SplashActivity.dbHandler.databaseToString());

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals("bluetooth")) {

             boolean bluetooth = sharedPreferences.getBoolean(key, false);
             Toast.makeText(this, toString(bluetooth), Toast.LENGTH_SHORT).show();

             if(bluetooth){
                 //Code to connect the phone to bluetooth
                 pairAndWaitForData();
                 Toast.makeText(getApplicationContext(), "Sync Complete", Toast.LENGTH_SHORT).show();
             }
        }
        else if(key.equals("gpssync")){
            boolean gpssync = sharedPreferences.getBoolean(key, false);
            Toast.makeText(this, toString(gpssync), Toast.LENGTH_SHORT).show();

            if(gpssync){
                //Code to read from bluetooth and store data
                fetchWeather();
                Toast.makeText(getApplicationContext(), "Weather Fetch Complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.layout.settings_view);
        }
    }

    private String toString(boolean value) {
        return value ? "true" : "false";
    }

    public void pairAndWaitForData() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) { // If there are paired devices
            for (BluetoothDevice device : pairedDevices) {  // Loop through paired devices
                if(device.getAddress().equals("00:06:66:6C:A6:38")){ //If this is our  device, connect to it!
                    //tell user to be patient while syncing data:
                    Toast.makeText(getApplicationContext(), "Syncing data...\n\nPlease wait.", Toast.LENGTH_SHORT).show();
                    Thread startServer = new ConnectThread(device, mBluetoothAdapter, SplashActivity.dbHandler);
                    startServer.run();
                }
            }
        }
    }

    //Call for the asynchronous GetWeatherTask to execute
    public void fetchWeather() {
        Toast.makeText(getApplicationContext(), "Fetching Weather data...\n\nPlease wait.", Toast.LENGTH_SHORT).show();
        Cursor cursor = SplashActivity.dbHandler.getInformation(SplashActivity.dbHandler);
        if(!cursor.moveToFirst()){
            Toast.makeText(getApplicationContext(), "Nothing found in DB during Weather Fetch.", Toast.LENGTH_SHORT).show();
        }
        else {
            //For each entry in the database, build URL from Lat and Long and call GetWeatherTask


            do {
                // Coordinates for Vancouver BC
                //double Van_lat = 49.24966, Van_lon = -123.119339;
                // Coordinates for Kelowna BC
                //double Kel_lat = 49.8880, Kel_lon = -119.4960;
                double latitude = cursor.getDouble(1);
                double longitude = cursor.getDouble(2);
                String units = "metric";
                String url = String.format("http://api.openweathermap.org/data/2.5/forecast/daily?lat=%f&lon=%f&units=%s&cnt=3&appid=%s",
                        latitude, longitude, units, APP_ID);
                new GetWeatherTask(latitude, longitude).execute(url);
            } while (cursor.moveToNext());
        }
    }


    private class GetWeatherTask extends AsyncTask<String, Void, PhotoLocation> {

        private double laty;
        private double longy;

        public GetWeatherTask(double latitude, double longitude) {
            this.laty = latitude;
            this.longy = longitude;
        }

        @Override
        protected PhotoLocation doInBackground(String... strings) {

            PhotoLocation pl = new PhotoLocation(this.laty, this.longy, 0);

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                // We create out JSONObject from the data
                JSONObject jObj = new JSONObject(builder.toString());

                //Set the City for this location
                JSONObject jCityObj = jObj.getJSONObject("city");
                pl.set_city(String.valueOf(jCityObj.getString("name")));

                //Inside the list array are the weather forecasts for the days
                JSONArray jArr = jObj.getJSONArray("list");

                JSONObject jDay1Forecast = jArr.getJSONObject(0);
                JSONObject jDay2Forecast = jArr.getJSONObject(1);
                JSONObject jDay3Forecast = jArr.getJSONObject(2);

                //Set time stamps for all 3 days
                pl.set_timestamp_day1(jDay1Forecast.getLong("dt"));
                pl.set_timestamp_day2(jDay2Forecast.getLong("dt"));
                pl.set_timestamp_day3(jDay3Forecast.getLong("dt"));

                //Set average daily temperature for all 3 days
                JSONObject jTempObj1 = jDay1Forecast.getJSONObject("temp");
                pl.set_temp_day1(jTempObj1.getDouble("day"));

                JSONObject jTempObj2 = jDay2Forecast.getJSONObject("temp");
                pl.set_temp_day2(jTempObj2.getDouble("day"));

                JSONObject jTempObj3 = jDay3Forecast.getJSONObject("temp");
                pl.set_temp_day3(jTempObj3.getDouble("day"));

                //Set average cloud conditions for all 3 days
                JSONArray jWeatherArr1 = jDay1Forecast.getJSONArray("weather");//
                JSONObject jWeatherObj1 = jWeatherArr1.getJSONObject(0);
                pl.set_clouds_day1(String.valueOf(jWeatherObj1.getString("description")));

                JSONArray jWeatherArr2 = jDay2Forecast.getJSONArray("weather");//
                JSONObject jWeatherObj2 = jWeatherArr2.getJSONObject(0);
                pl.set_clouds_day2(String.valueOf(jWeatherObj2.getString("description")));

                JSONArray jWeatherArr3 = jDay3Forecast.getJSONArray("weather");//
                JSONObject jWeatherObj3 = jWeatherArr3.getJSONObject(0);
                pl.set_clouds_day3(String.valueOf(jWeatherObj3.getString("description")));

                urlConnection.disconnect();
            } catch (IOException | JSONException e) { e.printStackTrace(); }

            return pl;
        }

        @Override
        protected void onPostExecute(PhotoLocation pl) {

            SplashActivity.dbHandler.updateEntry(pl);

            StringBuilder message = new StringBuilder();
            message.append("City name: " + pl.get_city());
            message.append("\tToday's date: ");
            message.append(pl.getStringDate((pl.get_timestamp_day1())));
            message.append("\tToday's timestamp: ");
            message.append(String.valueOf(pl.get_timestamp_day1()));
            message.append("\nTemp day1: ");
            message.append(String.valueOf(pl.get_temp_day1()));

            message.append("\nTomorrow's date: ");
            message.append(pl.getStringDate((pl.get_timestamp_day2())));
            message.append("\nTemp day2: ");
            message.append(String.valueOf(pl.get_temp_day2()));

            message.append("\nTemp day3: ");
            message.append(String.valueOf(pl.get_temp_day3()));

            Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();

            Log.i(TAG2, SplashActivity.dbHandler.databaseToString());

        }

    }





}

