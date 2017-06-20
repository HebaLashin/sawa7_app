package com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.uz.efir.muazzin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import com.uniquestartup.mohamedaliheeba.sawa7.BuildConfig;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.CONSTANT;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.Preferences;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.Schedule;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.dialog.CalculationSettingsDialog;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.util.LocaleManager;
import com.uniquestartup.mohamedaliheeba.sawa7.R;


public class Muazzin extends AppCompatActivity {
    // private static final String TAG = Muazzin.class.getSimpleName();
    private Preferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = Preferences.getInstance(this);
        LocaleManager.getInstance(this, true);

        setContentView(R.layout.activity_muazzin);
      //  getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/Latobold.ttf");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(" ");
        TextView txtlogo=(TextView)findViewById(R.id.MUZ_txttollbar);
        txtlogo.setTypeface(typeface);
        setSupportActionBar(toolbar);

        // Set up the ViewPager, attaching the adapter
        MuazzinAdapter muazzinAdapter = new MuazzinAdapter(getApplicationContext(),getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        assert pager != null;

        pager.setAdapter(muazzinAdapter);

        final SlidingTabLayout indicator = (SlidingTabLayout) findViewById(R.id.indicator);
        assert indicator != null;

        indicator.setDividerColors(R.color.accent);
        indicator.setViewPager(pager);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Utils.isRestartNeeded) {
                restartSelf();
                return;
            }

            if (mPreferences.isLocationSet()) {
                TextView notes = (TextView) findViewById(R.id.notes);
                assert notes != null;
                notes.setText(null);
            }
        }
    }

    /**
     * Restarts the app to apply new settings changes
     */
    private void restartSelf() {
//        Utils.updateWidgets(this);
        Utils.isRestartNeeded = false;

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,
                Calendar.getInstance().getTimeInMillis() + 300,
                PendingIntent.getActivity(Muazzin.this, 0, getIntent(), PendingIntent.FLAG_ONE_SHOT
                        | PendingIntent.FLAG_CANCEL_CURRENT));

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.activity_muazzin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!BuildConfig.DEBUG) {
            menu.removeGroup(R.id.menu_group_controller);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        short time = Schedule.today(this).nextTimeIndex();
        switch (item.getItemId()) {
            case R.id.menu_location_calculation:
                new CalculationSettingsDialog(Muazzin.this).show();
                break;
            case R.id.menu_previous:
                time--;
                if (time < CONSTANT.FAJR) {
                    time = CONSTANT.ISHAA;
                }
                if (CONSTANT.SUNRISE == time && mPreferences.dontNotifySunrise()) {
                    time = CONSTANT.FAJR;
                }
                NotificationService.notify(Muazzin.this, time, Schedule.today(Muazzin.this).getTimes()[time].getTimeInMillis());
                break;
            case R.id.menu_next:
                if (CONSTANT.SUNRISE == time && mPreferences.dontNotifySunrise()) {
                    time = CONSTANT.DHUHR;
                }
                NotificationService.notify(Muazzin.this, time, Schedule.today(Muazzin.this).getTimes()[time].getTimeInMillis());
                break;
            case R.id.menu_stop:
                NotificationService.cancelAll(Muazzin.this);
                break;
           /* case R.id.menu_settings:
                startActivity(new Intent(Muazzin.this, SettingsActivity.class));
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isRestartNeeded) {
            restartSelf();
            return;
        }
        Utils.setIsForeground(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.setIsForeground(false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
