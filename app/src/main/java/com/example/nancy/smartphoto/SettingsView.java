package com.example.nancy.smartphoto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SettingsView extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

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
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals("bluetooth")) {

             boolean bluetooth = sharedPreferences.getBoolean(key, false);
             Toast.makeText(this, toString(bluetooth), Toast.LENGTH_SHORT).show();

             if(bluetooth){
                 //Code to connect the phone to bluetooth
             }
        }
        else if(key.equals("gpssync")){
            boolean gpssync = sharedPreferences.getBoolean(key, false);
            Toast.makeText(this, toString(gpssync), Toast.LENGTH_SHORT).show();

            if(gpssync){
                //Code to read from bluetooth and store data
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

}

