package com.bindlesoft.calculatorsimulator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

public class settings extends AppCompatActivity {
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private boolean radOrDeg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferenceSettings = getPreferences(0);
        preferenceEditor = preferenceSettings.edit();
        boolean radians = preferenceSettings.getBoolean("radOrDeg",true);
        if(radians) {
            RadioButton rb = ((RadioButton)findViewById(R.id.optRad));
            rb.setChecked(true);
        } else {
            RadioButton rb = ((RadioButton)findViewById(R.id.optDeg));
            rb.setChecked(true);
        }

    }

    public void goToAbout(View view) {
        Intent intent = new Intent(this,about.class);
        startActivity(intent);
    }

    public void degRadClick(View view) {
        RadioButton rb = ((RadioButton)findViewById(R.id.optRad));
        if(rb.isChecked()) {
            preferenceEditor.putBoolean("radOrDeg",true);
        } else {
            preferenceEditor.putBoolean("radOrDeg",false);
        }
        preferenceEditor.commit();
    }
}
