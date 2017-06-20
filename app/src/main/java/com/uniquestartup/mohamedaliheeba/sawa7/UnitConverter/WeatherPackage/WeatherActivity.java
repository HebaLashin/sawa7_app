package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.WeatherPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.ErrorDialogFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.UniteConverterActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.WeatherPackage.model.Weather;

import java.text.DecimalFormat;


public class WeatherActivity extends AppCompatActivity {

    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg, textReslut;
    private TextView hum;
    private ImageView imgView;
    int tempCal = 0;
    private Weather weather;
    private Spinner spinner1, spinner2;
    Button btnSubmit;
    private EditText txtAmount;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String _from = "";
    String _to;
    Button btnToKelvin, btnToFahrenheit, btnToCelcius, btnFromCelcius, btnFromFahrenheit, btnFromKelvin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_tempretureconvert);

        btnToKelvin = (Button) findViewById(R.id.btnToKelvin);
        btnToFahrenheit = (Button) findViewById(R.id.btnToFahrenheit);
        btnToCelcius = (Button) findViewById(R.id.btnToCelcius);

        btnFromCelcius = (Button) findViewById(R.id.btnFromCelcius);
        btnFromFahrenheit = (Button) findViewById(R.id.btnFromFahrenheit);
        btnFromKelvin = (Button) findViewById(R.id.btnFromKelvin);

        txtAmount = (EditText) findViewById(R.id.T_txtAmount);
        textReslut = (TextView) findViewById(R.id.T_txresult);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");
        TextView txtlogo = (TextView) findViewById(R.id.H_txttollbar);
        txtlogo.setTypeface(typeface);
        Button btnback = (Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(WeatherActivity.this, UniteConverterActivity.class);
                startActivity(mainPage);
            }
        });
    }

    public void buttonFromPress(View v) {
        switch (v.getId()) {
            case R.id.btnFromCelcius:
                if (validate()) {
                    _from = "Celsius";
                    btnFromCelcius.setBackgroundResource(R.drawable.buttonraduisactive);
                    btnFromCelcius.setTextColor(this.getResources().getColor(R.color.white));

                    btnFromFahrenheit.setBackgroundResource(R.drawable.buttonraduis);
                    btnFromFahrenheit.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                    btnFromKelvin.setBackgroundResource(R.drawable.buttonraduis);
                    btnFromKelvin.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                } else {
                    return;
                }
                break;
            case R.id.btnFromFahrenheit:
                if (validate()) {
                    _from = "Fahrenheit";
                    btnFromFahrenheit.setBackgroundResource(R.drawable.buttonraduisactive);
                    btnFromFahrenheit.setTextColor(this.getResources().getColor(R.color.white));

                    btnFromCelcius.setBackgroundResource(R.drawable.buttonraduis);
                    btnFromCelcius.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                    btnFromKelvin.setBackgroundResource(R.drawable.buttonraduis);
                    btnFromKelvin.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                } else {
                    return;
                }

                break;
            case R.id.btnFromKelvin:
                if (validate()) {
                    _from = "Kelvin";
                    btnFromKelvin.setBackgroundResource(R.drawable.buttonraduisactive);
                    btnFromKelvin.setTextColor(this.getResources().getColor(R.color.white));

                    btnFromCelcius.setBackgroundResource(R.drawable.buttonraduis);
                    btnFromCelcius.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                    btnFromFahrenheit.setBackgroundResource(R.drawable.buttonraduis);
                    btnFromFahrenheit.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));
                } else {
                    return;
                }

                break;
        }
    }

    public void buttonToPress(View v) {
        switch (v.getId()) {
            case R.id.btnToCelcius:
                if (validate()) {
                    if (!_from.isEmpty()) {
                        btnToCelcius.setBackgroundResource(R.drawable.buttonraduisactive);
                        btnToCelcius.setTextColor(this.getResources().getColor(R.color.white));

                        btnToFahrenheit.setBackgroundResource(R.drawable.buttonraduis);
                        btnToFahrenheit.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                        btnToKelvin.setBackgroundResource(R.drawable.buttonraduis);
                        btnToKelvin.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                        _to = "Celsius";
                        String _amount = txtAmount.getText().toString();
                        String ConverterValue = getValue(_from, _to, _amount);
                        textReslut.setText(ConverterValue);
                    } else {
                        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                "Attention Please "
                                , " You Must Select Unit From ");

                        fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                    }
                } else {
                    return;
                }
                break;
            case R.id.btnToFahrenheit:
                if (validate()) {
                    if (!_from.isEmpty()) {
                        btnToFahrenheit.setBackgroundResource(R.drawable.buttonraduisactive);
                        btnToFahrenheit.setTextColor(this.getResources().getColor(R.color.white));

                        btnToCelcius.setBackgroundResource(R.drawable.buttonraduis);
                        btnToCelcius.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                        btnToKelvin.setBackgroundResource(R.drawable.buttonraduis);
                        btnToKelvin.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                        _to = "Fahrenheit";
                        String _amount = txtAmount.getText().toString();
                        String ConverterValue = getValue(_from, _to, _amount);
                        textReslut.setText(ConverterValue);
                    } else {
                        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                "Attention Please "
                                , " You Must Select Unit From ");

                        fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                    }
                } else {
                    return;
                }
                break;
            case R.id.btnToKelvin:
                if (validate()) {
                    if (!_from.isEmpty()) {
                        btnToKelvin.setBackgroundResource(R.drawable.buttonraduisactive);
                        btnToKelvin.setTextColor(this.getResources().getColor(R.color.white));

                        btnToCelcius.setBackgroundResource(R.drawable.buttonraduis);
                        btnToCelcius.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                        btnToFahrenheit.setBackgroundResource(R.drawable.buttonraduis);
                        btnToFahrenheit.setTextColor(this.getResources().getColor(R.color.buttonNotActivr));

                        _to = "Kelvin";
                        String _amount = txtAmount.getText().toString();
                        String ConverterValue = getValue(_from, _to, _amount);
                        textReslut.setText(ConverterValue);
                    } else {
                        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                "Attention Please "
                                , " You Must Select Unit From ");

                        fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                    }
                } else {
                    return;
                }
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getValue(String _valueFrom, String _ValueTo, String amount) {

        double amou = Double.parseDouble(amount);
        //String temperture=temp.getText().toString();

        if (_valueFrom.equals("Celsius") && _ValueTo.equals("Celsius")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Celsius") && _ValueTo.equals("Kelvin")) {
            double val = amou + 273.15;
            return String.valueOf(roundTwoDecimals(val));
        } else if (_valueFrom.equals("Celsius") && _ValueTo.equals("Fahrenheit")) {
            double val = (amou * 9 / 5) + 32;
            return String.valueOf(roundTwoDecimals(val));
        }////////////////////////////end////////////////////////////////////
        else if (_valueFrom.equals("Kelvin") && _ValueTo.equals("Kelvin")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Kelvin") && _ValueTo.equals("Fahrenheit")) {
            double val = (amou - 273.15) * 9 / 5 + 32;
            return String.valueOf(roundTwoDecimals(val));
        } else if (_valueFrom.equals("Kelvin") && _ValueTo.equals("Celsius")) {
            double val = amou - 273.15;
            return String.valueOf(roundTwoDecimals(val));
        }////////////////end////////////////////////////////////////////////
        else if (_valueFrom.equals("Fahrenheit") && _ValueTo.equals("Fahrenheit")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Fahrenheit") && _ValueTo.equals("Kelvin")) {
            double val = (amou - 32) * 5 / 9 + 273.15;
            return String.valueOf(roundTwoDecimals(val));
        } else if (_valueFrom.equals("Fahrenheit") && _ValueTo.equals("Celsius")) {
            double val = (amou - 32) * 5 / 9;
            return String.valueOf(roundTwoDecimals(val));
        }///////////////////////////////end/////////////////////////////////
        else {
            return "";
        }

    }

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Double.valueOf(twoDForm.format(d));
    }

    public boolean validate() {
        boolean valid = true;

        String email = txtAmount.getText().toString();


        if (email.isEmpty()) {
            txtAmount.setError("enter Amount Here");
            valid = false;
        } else {
            txtAmount.setError(null);
        }


        return valid;
    }
}
