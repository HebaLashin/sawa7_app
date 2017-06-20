package com.uniquestartup.mohamedaliheeba.sawa7.CoreApp;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uniquestartup.mohamedaliheeba.sawa7.R;

import android.speech.tts.TextToSpeech;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.ErrorDialogFragment;

import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {
    private Spinner spinner1;
    private TextToSpeech tts;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Latobold.ttf");

        EditText editT = (EditText) findViewById(R.id.etUserText);
        editT.setTypeface(typeface);
        spinner1 = (Spinner) findViewById(R.id.Tran_spinnerFrom);
        Button btn = (Button) findViewById(R.id.bTranslate);
        btn.setTypeface(typeface);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        et = (EditText) findViewById(R.id.etUserText);

       /* ((Button) findViewById(R.id.bSpeak)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                speakOut(((TextView) findViewById(R.id.tvTranslatedText)).getText().toString());
            }
        });*/

        ((Button) findViewById(R.id.bTranslate)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isNetworkConnected()) {
                    ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                            getString(R.string.title_error_no_network)
                            , getString(R.string.message_error_no_network));

                    fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                }else {
                    new bgStuff().execute();
                }

            }
        });
    }

    public String translate(String text) throws Exception {

        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
        Translate.setClientId("MohamedHeebaTranslator"); //Change this
        Translate.setClientSecret("PJyis+2fIIaEKHEx4pIvXofaXtu6Agp7LP0mLreVqiA="); //change
        String translatedText = "";
        if ((String.valueOf(spinner1.getSelectedItem()).equals("From Arabic To English"))) {
            translatedText = Translate.execute(text, Language.ENGLISH);
        } else {
            translatedText = Translate.execute(text, Language.ARABIC);
        }


        return translatedText;
    }


    public void onInit(int status) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.GERMAN);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                //speakOut("Ich");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    class bgStuff extends AsyncTask<Void, Void, Void> {

        String translatedText = "";

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {


                String text = et.getText().toString();
                translatedText = translate(text);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                translatedText = e.toString();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            ((TextView) findViewById(R.id.tvTranslatedText)).setText(translatedText);
            super.onPostExecute(result);
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
