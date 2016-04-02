package com.example.nancy.smartphoto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingsView extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

