package com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.uz.efir.muazzin;

import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.uniquestartup.mohamedaliheeba.sawa7.ElQuiblaNow.ParayerTimeFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.ElQuiblaNow.QuiblaCollection;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.Preferences;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.islam.adhanalarm.view.QiblaCompassView;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.net.sourceforge.jitl.Jitl;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.net.sourceforge.jitl.astro.Dms;
import com.uniquestartup.mohamedaliheeba.sawa7.R;

/**
 * Qibla compass fragment.
 */
@SuppressWarnings("deprecation")
// for SensorListener and SensorManager APIs
public class QiblaCompassFragment extends Fragment {
    private static final String TAG = QiblaCompassFragment.class.getSimpleName();
    private static final String PATTERN = "#.###";
    private static DecimalFormat sDecimalFormat;
    private static SensorManager sSensorManager;
    private static float sQiblaDirection = 0f;
    private static android.hardware.SensorListener sOrientationListener;
    private static boolean isTrackingOrientation = false;
    Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/sc.ttf");
        sSensorManager = (SensorManager) getActivity().getSystemService(Muazzin.SENSOR_SERVICE);
        try {
            sDecimalFormat = new DecimalFormat(PATTERN);
        } catch (AssertionError ae) {
            Log.wtf(TAG, "Could not construct DecimalFormat", ae);
            Log.d(TAG, "Will try with Locale.US");
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            if (format instanceof DecimalFormat) {
                sDecimalFormat = (DecimalFormat) format;
                sDecimalFormat.applyPattern(PATTERN);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_qibla, container, false);
        final QiblaCompassView qiblaCompassView = (QiblaCompassView) rootView.findViewById(R.id.qibla_compass);
        //TextView txt1 = (TextView) rootView.findViewById(R.id.bearing_north);
       // TextView txt2 = (TextView) rootView.findViewById(R.id.bearing_qibla);
        //txt1.setTypeface(typeface);
        //txt2.setTypeface(typeface);
       // qiblaCompassView.setConstants((txt1), getText(R.string.bearing_north), (txt2), getText(R.string.bearing_qibla));
        sOrientationListener = new android.hardware.SensorListener() {
            @Override
            public void onSensorChanged(int s, float v[]) {
                float northDirection = v[SensorManager.DATA_X];
                qiblaCompassView.setDirections(northDirection, sQiblaDirection);
            }

            @Override
            public void onAccuracyChanged(int s, int a) {
            }
        };

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDms();
        if (!isTrackingOrientation) {
            isTrackingOrientation = sSensorManager.registerListener(sOrientationListener,
                    SensorManager.SENSOR_ORIENTATION);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isTrackingOrientation) {
            sSensorManager.unregisterListener(sOrientationListener);
            isTrackingOrientation = false;
        }
    }

    /**
     * Add Latitude, Longitude and Qibla DMS location
     */
    private void updateDms() {
        com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.net.sourceforge.jitl.astro.Location location = Preferences.getInstance(getActivity()).getJitlLocation();

        Dms latitude = new Dms( QuiblaCollection.currentLatitude);
        Dms longitude = new Dms( QuiblaCollection.currentLongitude);
        location.setDegreeLat(QuiblaCollection.currentLatitude);
        location.setDegreeLong(QuiblaCollection.currentLongitude);
        Dms qibla = Jitl.getNorthQibla(location);
        sQiblaDirection = (float) qibla.getDecimalValue(com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.net.sourceforge.jitl.astro.Direction.NORTH);

        View rootView = getView();
        //TextView tv = (TextView) rootView.findViewById(R.id.current_latitude);
        //tv.setTypeface(typeface);
       // tv.setText(getString(R.string.degree_minute_second, latitude.getDegree(), latitude.getMinute(), sDecimalFormat.format(latitude.getSecond())));

        //tv = (TextView) rootView.findViewById(R.id.current_longitude);
       // tv.setText(getString(R.string.degree_minute_second, longitude.getDegree(),longitude.getMinute(), sDecimalFormat.format(longitude.getSecond())));
       // tv.setTypeface(typeface);

        //tv = (TextView) rootView.findViewById(R.id.current_qibla);
        //tv.setText(getString(R.string.degree_minute_second, qibla.getDegree(), qibla.getMinute(), sDecimalFormat.format(qibla.getSecond())));
        //tv.setTypeface(typeface);
    }
}
