package com.uniquestartup.mohamedaliheeba.sawa7.ElQuiblaNow;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Moham on 29/12/2016.
 */

public class QuiblaDirectionFragment extends Fragment implements SensorEventListener {

    static View V;
    private final Context context;
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;

    public QuiblaDirectionFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.quibla_fragment_main, container, false);
        image = (ImageView) V.findViewById(R.id.imageViewCompass);
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        return V;

    }

    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

}
