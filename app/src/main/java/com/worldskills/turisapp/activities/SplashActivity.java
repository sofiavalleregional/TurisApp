package com.worldskills.turisapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.worldskills.turisapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent pasar = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(pasar);
               finish();
            }
        }, 2000);


    }
    public void onPause(){
        super.onPause();

        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor guarda=datos.edit();

        guarda.putInt(MainActivity.FRAG_ACTIVO, 0);


        guarda.apply();
    }
}
