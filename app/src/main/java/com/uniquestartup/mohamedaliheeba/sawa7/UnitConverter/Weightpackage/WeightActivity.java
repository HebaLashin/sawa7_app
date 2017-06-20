package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Weightpackage;

import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uniquestartup.mohamedaliheeba.sawa7.CustomControls.NoDefaultSpinner;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.ErrorDialogFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.UniteConverterActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit.converter.Converter;

public class WeightActivity extends AppCompatActivity {

    private NoDefaultSpinner spinner1, spinner2;
    private Button btnSubmit, btnSpinnerFrom, btnSpinnerTo;
    private EditText txtAmount;
    private TextView textReslut;
    private TextView txtSpinnerFrom;
    private TextView txtSpinnerTo;
    private int check1 = 0;
    private int check2 = 0;
    private String _from, _to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_weightconvert);


        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");
        TextView txtlogo = (TextView) findViewById(R.id.H_txttollbar);
        txtlogo.setTypeface(typeface);
        Button btnback = (Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(WeightActivity.this, UniteConverterActivity.class);
                startActivity(mainPage);
            }
        });


    }

    // add items into spinner dynamically


    public void addListenerOnSpinnerItemSelection() {
        // spinner1 = (Spinner) findViewById(R.id.W_spinnerFrom);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (NoDefaultSpinner) findViewById(R.id.W_spinnerFrom);
        spinner2 = (NoDefaultSpinner) findViewById(R.id.W_spinnerTo);
        // btnSubmit = (Button) findViewById(R.id.W_btnSubmit);
        btnSpinnerFrom = (Button) findViewById(R.id.btnSpinnerfrom);
        btnSpinnerTo = (Button) findViewById(R.id.btnSpinnerto);

        txtAmount = (EditText) findViewById(R.id.W_txtAmount);
        textReslut = (TextView) findViewById(R.id.W_txresult);
        txtSpinnerFrom = (TextView) findViewById(R.id.txtspinnerFrom);
        txtSpinnerTo = (TextView) findViewById(R.id.txtspinnerTo);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (++check1 > 1) {
                    String _to = String.valueOf(spinner2.getSelectedItem());
                    txtSpinnerTo.setText(_to);
                    if (validate()) {
                        _from = String.valueOf(txtSpinnerFrom.getText());
                        _to = String.valueOf(txtSpinnerTo.getText());
                        if (!_from.isEmpty()) {
                            if (!_to.isEmpty()) {
                                String _amount = txtAmount.getText().toString();
                                String ConverterValue = getValue(_from, _to, _amount);
                                // DecimalFormat formatter = new DecimalFormat("#,###,###");
                                if (!ConverterValue.isEmpty()) {
                                    //   String yourFormattedString = formatter.format(Double.valueOf(ConverterValue));
                                    textReslut.setText(ConverterValue);
                                }
                            } else {
                                ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                        "Attention Please "
                                        , " You Must Select Length Unit To");

                                fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                            }
                        } else {
                            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                    "Attention Please "
                                    , " You Must Select Length Unit From");

                            fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                        }
                    } else {
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (++check2 > 1) {
                    _from = String.valueOf(spinner1.getSelectedItem());
                    _to = String.valueOf(spinner2.getSelectedItem());
                    txtSpinnerFrom.setText(_from);
                    if (validate()) {
                        _from = String.valueOf(txtSpinnerFrom.getText());
                        _to = String.valueOf(txtSpinnerTo.getText());
                        if (!_from.isEmpty()) {
                            if (!_to.isEmpty()) {
                                String _amount = txtAmount.getText().toString();
                                String ConverterValue = getValue(_from, _to, _amount);
                                // DecimalFormat formatter = new DecimalFormat("#,###,###");
                                if (!ConverterValue.isEmpty()) {
                                    //   String yourFormattedString = formatter.format(Double.valueOf(ConverterValue));
                                    textReslut.setText(ConverterValue);
                                }
                            } else {
                                ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                        "Attention Please "
                                        , " You Must Select Length Unit To");

                                fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                            }
                        } else {
                            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                    "Attention Please "
                                    , " You Must Select Length Unit From");

                            fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                        }
                    } else {
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnSpinnerFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spinner1.performClick();

            }
        });

        btnSpinnerTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spinner2.performClick();

            }
        });

        txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validate()) {
                    _from = String.valueOf(txtSpinnerFrom.getText());
                    _to = String.valueOf(txtSpinnerTo.getText());
                    if (!_from.isEmpty()) {
                        if (!_to.isEmpty()) {
                            String _amount = txtAmount.getText().toString();
                            String ConverterValue = getValue(_from, _to, _amount);
                            // DecimalFormat formatter = new DecimalFormat("#,###,###");
                            if (!ConverterValue.isEmpty()) {
                                //   String yourFormattedString = formatter.format(Double.valueOf(ConverterValue));
                                textReslut.setText(ConverterValue);
                            }
                        } else {
                            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                    "Attention Please "
                                    , " You Must Select Length Unit To");

                            fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                        }
                    } else {
                        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                                "Attention Please "
                                , " You Must Select Length Unit From");

                        fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
                    }
                } else {
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        if (_valueFrom.equals("Tonne") && _ValueTo.equals("Tonne")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Kilogram")) {
            double val = amou * 1000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Gram")) {
            double val = amou * 1e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Milligram")) {
            double val = amou * 1e+9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Microgram")) {
            double val = amou * 1e+12;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 0.984207;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("US ton")) {
            double val = amou * 1.10231;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Stone")) {
            double val = amou * 157.473;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Pound")) {
            double val = amou * 2204.62;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Tonne") && _ValueTo.equals("Ounce")) {
            double val = amou * 35274;
            return String.valueOf(val);
        }////////////////////////////end////////////////////////////////////
        else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Kilogram")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Tonne")) {
            double val = amou * 0.001;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Gram")) {
            double val = amou * 1000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Milligram")) {
            double val = amou * 1e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Microgram")) {
            double val = amou * 1e+9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 0.000984207;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("US ton")) {
            double val = amou * 0.00110231;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Stone")) {
            double val = amou * 0.157473;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Pound")) {
            double val = amou * 2.20462;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Kilogram") && _ValueTo.equals("Ounce")) {
            double val = amou * 35.274;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Gram") && _ValueTo.equals("Gram")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Tonne")) {
            double val = amou * 1e-6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Kilogram")) {
            double val = amou * 0.001;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Milligram")) {
            double val = amou * 1000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Microgram")) {
            double val = amou * 1e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 9.8421e-7;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("US ton")) {
            double val = amou * 1.1023e-6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Stone")) {
            double val = amou * 0.000157473;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Pound")) {
            double val = amou * 0.00220462;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Gram") && _ValueTo.equals("Ounce")) {
            double val = amou * 0.035274;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Milligram")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Tonne")) {
            double val = amou * 1e-9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Kilogram")) {
            double val = amou * 1e-6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Gram")) {
            double val = amou * 0.001;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Microgram")) {
            double val = amou * 1000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 9.8421e-10;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("US ton")) {
            double val = amou * 1.1023e-9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Stone")) {
            double val = amou * 1.5747e-7;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Pound")) {
            double val = amou * 2.2046e-6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Milligram") && _ValueTo.equals("Ounce")) {
            double val = amou * 3.5274e-5;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Microgram")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Tonne")) {
            double val = amou * 1e-12;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Kilogram")) {
            double val = amou * 1e-9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Gram")) {
            double val = amou * 1e-6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Milligram")) {
            double val = amou * 0.001;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 9.8421e-13;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("US ton")) {
            double val = amou * 1.1023e-12;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Stone")) {
            double val = amou * 1.5747e-10;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Pound")) {
            double val = amou * 2.2046e-9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Microgram") && _ValueTo.equals("Ounce")) {
            double val = amou * 3.5274e-8;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Imperial ton")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Tonne")) {
            double val = amou * 1.01605;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Kilogram")) {
            double val = amou * 1016.05;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Gram")) {
            double val = amou * 1.016e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Milligram")) {
            double val = amou * 1.016e+9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Microgram")) {
            double val = amou * 1.016e+12;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("US ton")) {
            double val = amou * 1.12;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Stone")) {
            double val = amou * 160;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Pound")) {
            double val = amou * 2240;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Imperial ton") && _ValueTo.equals("Ounce")) {
            double val = amou * 35840;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("US ton") && _ValueTo.equals("US ton")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Tonne")) {
            double val = amou * 0.907185;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Kilogram")) {
            double val = amou * 907.185;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Gram")) {
            double val = amou * 907185;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Milligram")) {
            double val = amou * 9.072e+8;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Microgram")) {
            double val = amou * 9.072e+11;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 0.892857;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Stone")) {
            double val = amou * 142.857;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Pound")) {
            double val = amou * 2000;
            return String.valueOf(val);
        } else if (_valueFrom.equals("US ton") && _ValueTo.equals("Ounce")) {
            double val = amou * 32000;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Stone") && _ValueTo.equals("Stone")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Tonne")) {
            double val = amou * 0.00635029;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Kilogram")) {
            double val = amou * 6.35029;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Gram")) {
            double val = amou * 6350.29;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Milligram")) {
            double val = amou * 6.35e+6;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 0.00625;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("US ton")) {
            double val = amou * 0.007;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Microgram")) {
            double val = amou * 6.35e+9;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Pound")) {
            double val = amou * 14;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Stone") && _ValueTo.equals("Ounce")) {
            double val = amou * 224;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Pound") && _ValueTo.equals("Pound")) {
            return String.valueOf(amou);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Tonne")) {
            double val = amou * 0.000453592;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Kilogram")) {
            double val = amou * 0.453592;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Gram")) {
            double val = amou * 453.592;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Milligram")) {
            double val = amou * 453592;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 0.000446429;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("US ton")) {
            double val = amou * 0.0005;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Microgram")) {
            double val = amou * 4.536e+8;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Stone")) {
            double val = amou * 0.0714286;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Pound") && _ValueTo.equals("Ounce")) {
            double val = amou * 16;
            return String.valueOf(val);
        }////////////////////////end//////////////////////////////////////////////
        else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Ounce")) {
            double val = amou;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Kilogram")) {
            double val = amou * 0.0283495;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Gram")) {
            double val = amou * 28.3495;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Milligram")) {
            double val = amou * 28349.5;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Imperial ton")) {
            double val = amou * 2.7902e-5;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("US ton")) {
            double val = amou * 3.125e-5;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Microgram")) {
            double val = amou * 2.835e+7;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Stone")) {
            double val = amou * 0.00446429;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Pound")) {
            double val = amou * 0.0625;
            return String.valueOf(val);
        } else if (_valueFrom.equals("Ounce") && _ValueTo.equals("Tonne")) {
            double val = amou * 2.835e-5;
            return String.valueOf(val);
        } else {
            return "That Unable Error";
        }

    }
}
