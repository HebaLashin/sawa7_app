package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_activities.MainCurrencyActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.LenghtPackage.LengthActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.WeatherPackage.WeatherActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Weightpackage.WeightActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;
import java.io.File;
import de.hdodenhof.circleimageview.CircleImageView;


public class UniteConverterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView _btnCurrency;
    CircleImageView _btnWeather;
    CircleImageView _btnWeight;
    CircleImageView _btnlenght;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unite_converter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView txtlogo = (TextView) findViewById(R.id.H_txttollbar);
        txtlogo.setTypeface(typeface);
        Button btnback = (Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(UniteConverterActivity.this, HomeActivity.class);
                startActivity(mainPage);
            }
        });

        _btnCurrency = (CircleImageView) findViewById(R.id.U_btnCurrency);
        _btnWeather = (CircleImageView) findViewById(R.id.U_btnTemperature);
        _btnWeight = (CircleImageView) findViewById(R.id.U_btnWeight);
        _btnlenght = (CircleImageView) findViewById(R.id.U_btnLength);

        _btnCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(UniteConverterActivity.this, MainCurrencyActivity.class);
                startActivity(trabslateCa);
            }
        });
        _btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(UniteConverterActivity.this, WeatherActivity.class);
                startActivity(trabslateCa);
            }
        });
        _btnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(UniteConverterActivity.this, WeightActivity.class);
                startActivity(trabslateCa);
            }
        });
        _btnlenght.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(UniteConverterActivity.this, LengthActivity.class);
                startActivity(trabslateCa);
            }
        });

    }

    public void clearApplicationData() {

        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {

            String[] fileNames = applicationDirectory.list();

            for (String fileName : fileNames) {

                if (!fileName.equals("lib")) {

                    deleteFile(new File(applicationDirectory, fileName));

                }

            }

        }

    }

    public static boolean deleteFile(File file) {

        boolean deletedAll = true;

        if (file != null) {

            if (file.isDirectory()) {

                String[] children = file.list();

                for (int i = 0; i < children.length; i++) {

                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;

                }

            } else {

                deletedAll = file.delete();

            }

        }

        return deletedAll;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        // return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
