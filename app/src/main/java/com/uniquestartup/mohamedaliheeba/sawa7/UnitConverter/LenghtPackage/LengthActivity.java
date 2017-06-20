package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.LenghtPackage;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.CustomControls.NoDefaultSpinner;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_activities.CustomOnItemSelectedListener;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.ErrorDialogFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.UniteConverterActivity;

import java.text.DecimalFormat;


public class LengthActivity extends AppCompatActivity {

    private NoDefaultSpinner spinner1, spinner2;
    private Button btnSubmit, btnFrom, btnTo;
    private EditText txtAmount;
    private TextView textReslut, txtFrom, txtTo;
    String _from = "", _to = "";
    private int check1 = 0;
    private int check2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_lengthonvert);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");
        txtFrom = (TextView) findViewById(R.id.txtFromLength);
        txtTo = (TextView) findViewById(R.id.txtToLength);
        txtFrom.setTypeface(typeface);
        txtTo.setTypeface(typeface);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        btnFrom = (Button) findViewById(R.id.btnFromLength);
        btnTo = (Button) findViewById(R.id.btnToLength);
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner1.performClick();
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner2.performClick();
            }
        });


        TextView txtlogo = (TextView) findViewById(R.id.H_txttollbar);
        txtlogo.setTypeface(typeface);
        Button btnback = (Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(LengthActivity.this, UniteConverterActivity.class);
                startActivity(mainPage);
            }
        });


    }
    // add items into spinner dynamically


    public void addListenerOnSpinnerItemSelection() {
        //spinner1 = (Spinner) findViewById(R.id.L_spinnerFrom);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListenerLength());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (NoDefaultSpinner) findViewById(R.id.L_spinnerFrom);
        spinner2 = (NoDefaultSpinner) findViewById(R.id.L_spinnerTo);
        btnSubmit = (Button) findViewById(R.id.L_btnSubmit);
        txtAmount = (EditText) findViewById(R.id.L_txtAmount);
        textReslut = (TextView) findViewById(R.id.L_txresult);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (++check1 > 1) {
                    String _from = String.valueOf(spinner1.getSelectedItem());
                    txtFrom.setText(_from);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (++check2 > 1) {
                    String _to = String.valueOf(spinner2.getSelectedItem());
                    txtTo.setText(_to);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validate()) {

                    _from = String.valueOf(txtFrom.getText());
                    _to = String.valueOf(txtTo.getText());
                    if (!_from.isEmpty()) {
                        if (!_to.isEmpty()) {
                            _from = String.valueOf(spinner1.getSelectedItem());
                            _to = String.valueOf(spinner2.getSelectedItem());
                            String _amount = txtAmount.getText().toString();
                            String ConverterValue = getValue(_from, _to, _amount);
                            // DecimalFormat formatter = new DecimalFormat("#,###,###");
                            //String yourFormattedString = formatter.format(Double.valueOf(ConverterValue));
                            textReslut.setText(ConverterValue);
                        } else {
                            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                    "Attention Please "
                                    , " You Must Select Unit To");

                            fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                        }
                    } else {
                        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                "Attention Please "
                                , " You Must Select Unit From");

                        fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                    }
                } else {
                    return;
                }
            }

        });
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

    public String getValue(String _valueFrom, String _ValueTo, String amount) {

        double amou = Double.parseDouble(amount);

        if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Millimeter")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Centimeter")) {
            double val = amou * 0.1;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Meter")) {
            double val = amou * 0.001;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Kilometer")) {
            double val = amou * 1e-6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Inch")) {
            double val = amou * 0.0393701;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Foot")) {
            double val = amou * 0.00328084;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Yard")) {
            double val = amou * 0.00109361;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Millimeter") && _ValueTo.equals("Mile")) {
            double val = amou * 6.2137e-7;
            return String.valueOf(val);
        }
        ///////////////////end////////////////
        else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Centimeter")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Millimeter")) {
            double val = amou * 10;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Meter")) {
            double val = amou * 0.01;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Kilometer")) {
            double val = amou * 1e-5;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Inch")) {
            double val = amou * 0.393701;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Foot")) {
            double val = amou * 0.0328084;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Yard")) {
            double val = amou * 0.0109361;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Centimeter") && _ValueTo.equals("Mile")) {
            double val = amou * 6.2137e-6;
            return String.valueOf(val);
        }///////////////
        else if (_valueFrom.equals("Meter") && _ValueTo.equals("Meter")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Millimeter")) {
            double val = amou * 1000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Centimeter")) {
            double val = amou * 100;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Kilometer")) {
            double val = amou * 0.001;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Inch")) {
            double val = amou * 39.3701;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Foot")) {
            double val = amou * 3.28084;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Yard")) {
            double val = amou * 1.09361;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Meter") && _ValueTo.equals("Mile")) {
            double val = amou * 0.000621371;
            return String.valueOf(val);
        }////////////////////
        else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Kilometer")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Millimeter")) {
            double val = amou * 1e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Centimeter")) {
            double val = amou * 100000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Meter")) {
            double val = amou * 1000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Inch")) {
            double val = amou * 39370.1;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Foot")) {
            double val = amou * 3280.84;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Yard")) {
            double val = amou * 1093.61;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilometer") && _ValueTo.equals("Mile")) {
            double val = amou * 0.621371;
            return String.valueOf(val);
        }///////////////
        else if (_valueFrom.equals("Inch") && _ValueTo.equals("Inch")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Millimeter")) {
            double val = amou * 25.4;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Centimeter")) {
            double val = amou * 2.54;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Meter")) {
            double val = amou * 0.0254;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Kilometer")) {
            double val = amou * 2.54e-5;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Foot")) {
            double val = amou * 0.0833333;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Yard")) {
            double val = amou * 0.0277778;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Inch") && _ValueTo.equals("Mile")) {
            double val = amou * 1.5783e-5;
            return String.valueOf(val);
        }///////////////
        else if (_valueFrom.equals("Foot") && _ValueTo.equals("Foot")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Millimeter")) {
            double val = amou * 304.8;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Centimeter")) {
            double val = amou * 30.48;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Meter")) {
            double val = amou * 0.3048;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Kilometer")) {
            double val = amou * 0.0003048;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Inch")) {
            double val = amou * 12;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Yard")) {
            double val = amou * 0.333333;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Foot") && _ValueTo.equals("Mile")) {
            double val = amou * 0.000189394;
            return String.valueOf(val);
        }//////////////
        else if (_valueFrom.equals("Yard") && _ValueTo.equals("Yard")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Millimeter")) {
            double val = amou * 914.4;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Centimeter")) {
            double val = amou * 91.44;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Meter")) {
            double val = amou * 0.9144;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Kilometer")) {
            double val = amou * 0.0009144;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Inch")) {
            double val = amou * 36;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Foot")) {
            double val = amou * 3;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Yard") && _ValueTo.equals("Mile")) {
            double val = amou * 0.000568182;
            return String.valueOf(val);
        }/////
        else if (_valueFrom.equals("Mile") && _ValueTo.equals("Mile")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Millimeter")) {
            double val = amou * 1.609e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Centimeter")) {
            double val = amou * 160934;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Meter")) {
            double val = amou * 1609.34;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Kilometer")) {
            double val = amou * 1.60934;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Inch")) {
            double val = amou * 63360;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Foot")) {
            double val = amou * 5280;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Mile") && _ValueTo.equals("Yard")) {
            double val = amou * 1760;
            return String.valueOf(val);
        }
        ///////////////////end////////////////
        else {
            return "0";
        }
    }
}
