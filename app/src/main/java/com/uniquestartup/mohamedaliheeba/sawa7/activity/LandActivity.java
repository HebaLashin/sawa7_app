package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.LoginTwiter;
import com.uniquestartup.mohamedaliheeba.sawa7.R;

public class LandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_land);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");

        Button _signIn=(Button)findViewById(R.id.btnSigninLand);
        _signIn.setTypeface(typeface);
        _signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callActivity = new Intent(LandActivity.this, LoginActivity.class);
                startActivity(callActivity);
            }
        });

        Button _signUp=(Button)findViewById(R.id.btnSignupLand);
        _signUp.setTypeface(typeface);
        _signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callActivity = new Intent(LandActivity.this, SignupActivity.class);
                startActivity(callActivity);
            }
        });

        Button _connectWithTwitter=(Button)findViewById(R.id.btnConnectWithTwitterLand);
        _connectWithTwitter.setTypeface(typeface);
        _connectWithTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callActivity = new Intent(LandActivity.this, LandLogin.class);
                startActivity(callActivity);
            }
        });


    }
}
