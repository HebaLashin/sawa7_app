package com.uniquestartup.mohamedaliheeba.sawa7.Translation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;

/**
 * Created by Moham on 29/12/2016.
 */

public class TwoFragment extends Fragment {
    static View V;
    private final Context context;
    public TwoFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void onResume() {
        Log.i("Test", Time.getCurrentTimezone().toString());

        Log.i("Test", Time.getCurrentTimezone().toString());
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.phrase_fragment_main, container, false);
        return V;

    }
}
