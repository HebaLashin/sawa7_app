package com.uniquestartup.mohamedaliheeba.sawa7.Translation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.ErrorDialogFragment;


import java.util.Locale;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Moham on 29/12/2016.
 */

public class OneFragment extends Fragment {

    private TextToSpeech tts;
    static EditText etEnglish, etArabic;
    Typeface typeface;
    static View view;
    private final Context context;
    String evalue = "";
    ClipboardManager clipboard;
    Button bEPaste, bECopye, bESound, bAPaste, bACopye, bASound;

    public OneFragment(Context context) {
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
        view = inflater.inflate(R.layout.targama_fragment_main, container, false);
        tts = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/myriad.otf");
        clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

        etArabic = (EditText) view.findViewById(R.id.editTranslationArabic);
        etEnglish = (EditText) view.findViewById(R.id.editTranslationEnglish);

        etArabic.setTypeface(typeface);
        etEnglish.setTypeface(typeface);

        bEPaste = (Button) view.findViewById(R.id.btnEnglishPaste);
        bECopye = (Button) view.findViewById(R.id.btnEnglishCopy);
        bESound = (Button) view.findViewById(R.id.btnEnglishSpeech);
        bAPaste = (Button) view.findViewById(R.id.btnArabicPaste);
        bACopye = (Button) view.findViewById(R.id.btnArabicCopy);
        bASound = (Button) view.findViewById(R.id.btnArabicSpeech);

        bESound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEnglish.getText().toString().matches("")) {
                    speakOut(etEnglish.getText().toString());
                }
            }
        });

        bECopye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEnglish.getText().toString().matches("")) {
                    clipboard.setText(etEnglish.getText().toString());
                }
            }
        });

        bEPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etEnglish.setText(clipboard.getText().toString());

            }
        });

        bACopye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etArabic.getText().toString().matches("")) {
                    clipboard.setText(etArabic.getText().toString());
                }
            }
        });

        bAPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etArabic.setText(clipboard.getText().toString());
            }

        });
        etArabic.setTypeface(typeface);
        etEnglish.setTypeface(typeface);
        if (!isNetworkConnected()) {
            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                    getString(R.string.title_error_no_network)
                    , getString(R.string.message_error_no_network));

            // fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
        } else {

            etArabic.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    evalue = "1";
                    return false;
                }
            });

            etEnglish.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    evalue = "2";
                    return false;
                }
            });
            etArabic.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        if (evalue.equals("1")) {
                            String[] data = {etArabic.getText().toString(), "ARB"};
                            new bgStuff().execute(data);
                        }

                    }


                }
            });
            etEnglish.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        if (evalue.equals("2")) {
                            String[] data = {etEnglish.getText().toString(), "ENG"};
                            new bgStuff().execute(data);
                        }
                    }
                }
            });
        }

        return view;
    }

    public String translate(String text, String language) throws Exception {

        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
        Translate.setClientId("MohamedHeebaTranslator"); //Change this
        Translate.setClientSecret("PJyis+2fIIaEKHEx4pIvXofaXtu6Agp7LP0mLreVqiA="); //change
        String translatedText = "";
        if ((language.equals("ENG"))) {
            translatedText = Translate.execute(text, Language.ARABIC);
        } else {
            translatedText = Translate.execute(text, Language.ENGLISH);
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

    class bgStuff extends AsyncTask<String, Void, Void> {

        String translatedText = "";
        String translatedLanguage = "";

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                translatedLanguage = params[1];
                translatedText = translate(params[0], params[1]);

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
            if ((translatedLanguage.equals("ENG"))) {
                ((TextView) view.findViewById(R.id.editTranslationArabic)).setText(translatedText);
            } else {
                ((TextView) view.findViewById(R.id.editTranslationEnglish)).setText(translatedText);
            }

            super.onPostExecute(result);
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
