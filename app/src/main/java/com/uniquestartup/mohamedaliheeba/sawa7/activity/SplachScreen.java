package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.PrefManager;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.WelcomeActivity;

public class SplachScreen extends AppCompatActivity  {

    static int TIMESLEEP = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splach_screen);
       // Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/sc.ttf");
       // TextView txt = (TextView) findViewById(R.id.S_textView);
        //txt.setTypeface(typeface);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplachScreen.this, LandActivity.class);
                startActivity(i);

            }
        }, TIMESLEEP);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onStop() {
        super.onStop();

    }
}
