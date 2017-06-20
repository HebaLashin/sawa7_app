package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.share.widget.ShareDialog;
import com.uniquestartup.mohamedaliheeba.sawa7.CoreApp.TranslateActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.uz.efir.muazzin.Muazzin;
import com.uniquestartup.mohamedaliheeba.sawa7.GPSTrack.LocationValue;
import com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass.DownloadImage;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.LoginFacebook;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.UniteConverterActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    Button _translate;
    Button _elQuibla;
    Button _location;
    Button _uniteConvert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _translate = (Button)findViewById(R.id.M_btnTranslate);
        _elQuibla  = (Button)findViewById(R.id.M_btnElQuibla);
        _location  = (Button)findViewById(R.id.M_btngetCurrntLocation);
        _uniteConvert  = (Button)findViewById(R.id.M_btnUniteConvert);

        _translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(MainActivity.this, TranslateActivity.class);
                startActivity(trabslateCa);
            }
        });
        _elQuibla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elquiblaCa = new Intent(MainActivity.this, Muazzin.class);
                startActivity(elquiblaCa);
            }
        });

        _location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationCa = new Intent(MainActivity.this, LocationValue.class);
                startActivity(locationCa);
            }
        });

        _uniteConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationCa = new Intent(MainActivity.this, UniteConverterActivity.class);
                startActivity(locationCa);
            }
        });

    }


}
