package com.uniquestartup.mohamedaliheeba.sawa7.ElQuiblaNow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.uz.efir.muazzin.PrayerTimesFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.R;


import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Moham on 29/12/2016.
 */

public class ParayerTimeFragment extends Fragment {

    static View view;
    private final Context context;
    private TextView txtfagir, txtzuhar, txtaser, txtmagrib, txtishaa, txtCurrentDate, txtlocation;
    Button btnNext, btnPervious;
    private static int _increment = 0, _decrement = 0;
    String dt;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ParayerTimeFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_elquibla_main, container, false);


        txtfagir = (TextView) view.findViewById(R.id.fajrtimePray);
        txtaser = (TextView) view.findViewById(R.id.asrtimePray);
        txtishaa = (TextView) view.findViewById(R.id.ishaatimePray);
        txtmagrib = (TextView) view.findViewById(R.id.maghribtimePray);
        txtzuhar = (TextView) view.findViewById(R.id.zuhartimePray);
        txtlocation = (TextView) view.findViewById(R.id.txtLocation);
        btnNext = (Button) view.findViewById(R.id.btnPrayNext);
        btnPervious = (Button) view.findViewById(R.id.btnPrayPervious);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(1);
            }
        });

        btnPervious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime(-1);
            }
        });

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        dt = sdf.format(cal.getTime());

        getTime(0);
        return view;

    }

    // onClick method for the button
    public void getTime(int numDay) {
        // Retrive lat, lng using location API
        double latitude = QuiblaCollection.currentLatitude;
        double longitude = QuiblaCollection.currentLongitude;
        double timezone = (Calendar.getInstance().getTimeZone().getOffset(Calendar.getInstance().getTimeInMillis())) / (1000 * 60 * 60);
        PrayTime prayers = new PrayTime();
        txtlocation.setText(QuiblaCollection.city + " , " + QuiblaCollection.country);
        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Makkah);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);



        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, numDay);


        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, numDay);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date



        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        String format = new SimpleDateFormat("EEEE, dd MMM yyyy").format(cal.getTime());
        txtCurrentDate = (TextView) view.findViewById(R.id.currentDate);
        txtCurrentDate.setText(format);

        ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                longitude, timezone);
        ArrayList prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            switch (prayerNames.get(i).toString()) {
                case "Fajr":
                    txtfagir.setText(prayerTimes.get(i).toString());
                    break;
                case "Dhuhr":
                    txtzuhar.setText(prayerTimes.get(i).toString());
                    break;
                case "Asr":
                    txtaser.setText(prayerTimes.get(i).toString());
                    break;
                case "Maghrib":
                    txtmagrib.setText(prayerTimes.get(i).toString());
                    break;
                case "Isha":
                    txtishaa.setText(prayerTimes.get(i).toString());
                    break;
            }

        }
    }


}
