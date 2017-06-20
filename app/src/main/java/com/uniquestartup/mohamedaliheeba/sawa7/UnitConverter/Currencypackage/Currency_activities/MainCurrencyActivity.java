package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.CurrencySQLiteOpenHelper;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.CurrencyService;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_adapters.CurrencyAdapter;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_adapters.SpinAdapter;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_models.Currency;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_models.RateResponse;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.ErrorDialogFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.UniteConverterActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Weightpackage.CustomOnItemSelectedListener;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainCurrencyActivity extends AppCompatActivity {

    private static final String TAG = "CurrencyActivity";
    public static final String KEY_CURRENCY = "key_currency";
    public static final String KEY_AMOUNT = "key_amount";
    public static final String KEY_TIMESTAMP = "key_timestamp";
    private CurrencyService mCurrencyService;
    private CurrencySQLiteOpenHelper mHelper;
    CurrencyAdapter mCurrencyAdapter;
    HashMap<String, String> mCurrencyMappings;
    ArrayList<Currency> mCurrencies;
    RateResponse mResponse;
    private Spinner spinner1, spinner2;
    private Button btnSubmit, btnFrom, btnTO;
    private EditText txtAmount;
    private TextView textReslut, txtFrom, txtTo;
    private SpinAdapter adapter;
    SmoothProgressBar mProgressBar;
    String key;

    private static boolean flagFrome = false;
    private static boolean flagTo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_currencyconvert);
        key = getString(R.string.key);

        mProgressBar = (SmoothProgressBar) findViewById(R.id.progress_loading);
        mProgressBar.setVisibility(View.INVISIBLE);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://openexchangerates.org/api").build();
        mCurrencyService = adapter.create(CurrencyService.class);
        downloadData();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");
        TextView txtlogo = (TextView) findViewById(R.id.H_txttollbar);
        txtlogo.setTypeface(typeface);
        Button btnback = (Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(MainCurrencyActivity.this, UniteConverterActivity.class);
                startActivity(mainPage);
            }
        });

    }

    // add items into spinner dynamically
    public void addListenerOnSpinnerItemSelection() {
        //spinner1 = (Spinner) findViewById(R.id.C_spinnerFrom);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        spinner1 = (Spinner) findViewById(R.id.C_spinnerFrom);
        spinner2 = (Spinner) findViewById(R.id.C_spinnerTo);
        btnSubmit = (Button) findViewById(R.id.C_btnSubmit);
        btnFrom = (Button) findViewById(R.id.btnCurrencySpinnerfrom);
        btnTO = (Button) findViewById(R.id.btnCurrencySpinnerTo);
        txtAmount = (EditText) findViewById(R.id.C_txtAmount);
        textReslut = (TextView) findViewById(R.id.C_txresult);
        txtFrom = (TextView) findViewById(R.id.txtViewCurrencyFrom);
        txtTo = (TextView) findViewById(R.id.txtViewCurrencyTO);

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner1.performClick();
                flagFrome = true;
            }
        });

        btnTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner2.performClick();
                flagTo = true;
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (flagTo) {
                    Currency _to = (Currency) (spinner2.getSelectedItem());
                    txtTo.setText(_to.getName());
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
                // your code here
                if (flagFrome) {
                    Currency _from = (Currency) (spinner1.getSelectedItem());
                    txtFrom.setText(_from.getName());
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
                    Currency _from = (Currency) (spinner1.getSelectedItem());
                    Currency _to = (Currency) (spinner2.getSelectedItem());
                    String _amount = txtAmount.getText().toString();
                    txtFrom.setText(_from.getName());
                    txtTo.setText(_to.getName());
                    String ConverterValue = getValue(_from, _to, _amount);
                    textReslut.setText(ConverterValue);
                } else {
                    return;
                }
            }

        });
    }

    public String getValue(Currency _valueFrom, Currency _ValueTo, String amount) {
        double amou = Double.parseDouble(amount);
        double val = (_ValueTo.getRate() / _valueFrom.getRate()) * amou;
        return String.valueOf(val);
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void downloadData() {

        if (!isNetworkConnected()) {
            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                    getString(R.string.title_error_no_network)
                    , getString(R.string.message_error_no_network));

            fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
        } else {

            mProgressBar.setVisibility(View.VISIBLE);

            mCurrencyService.getCurrencyMappings(key, new Callback<HashMap<String, String>>() {
                @Override
                public void success(HashMap<String, String> hashMaps, Response response) {
                    Log.i(TAG, "Got rates:" + hashMaps.toString());
                    mCurrencyMappings = hashMaps;

                    mCurrencyService.getRates(key, new Callback<RateResponse>() {
                        @Override
                        public void success(RateResponse rateResponse, Response response) {
                            Log.i(TAG, "Got names: " + rateResponse.getRates().toString());

                            mResponse = rateResponse;

                            Log.i(TAG, "Timestamp: " + rateResponse.getTimestamp());

                            //SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                            // prefs.edit().putLong(KEY_TIMESTAMP, rateResponse.getTimestamp()).apply();

                            if (mCurrencyMappings != null) {
                                mCurrencies = Currency.generateCurrencies(mCurrencyMappings, mResponse);

                                // Log.i(TAG, "Generated Currencies: " + Arrays.toString(mCurrencies.toArray()));

                                //  mHelper.addCurrencies(mCurrencies);
                                //  mHelper.addCurrencies(mCurrencies);

                                // mCurrencyAdapter = new CurrencyAdapter(MainCurrencyActivity.this);
                               /* List<String> currencys = new ArrayList<String>();
                                for(int i=0;i<mCurrencies.size();i++){
                                    currencys.add(mCurrencies.get(i).getName().toString());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainCurrencyActivity.this,
                                        android.R.layout.simple_spinner_item, currencys);
*/
                                Currency[] mCurrenciesArrAy = new Currency[mCurrencies.size()];
                                for (int i = 0; i < mCurrencies.size(); i++) {
                                    mCurrenciesArrAy[i] = mCurrencies.get(i);
                                }
                                ArrayList<Currency> list = new ArrayList<Currency>(Arrays.asList(mCurrenciesArrAy));


                                if (list.size() > 0) {
                                    Collections.sort(list, new Comparator<Currency>() {
                                        @Override
                                        public int compare(final Currency object1, final Currency object2) {
                                            return object1.getName().compareTo(object2.getName());
                                        }
                                    });
                                }
                                mCurrenciesArrAy = list.toArray(new Currency[list.size()]);

                                adapter = new SpinAdapter(MainCurrencyActivity.this,
                                        R.layout.listview_itemcurrrency_row,
                                        mCurrenciesArrAy);

                                spinner1.setAdapter(adapter);
                                spinner2.setAdapter(adapter);

                                mProgressBar.setVisibility(View.INVISIBLE);
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, error.getLocalizedMessage());
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getLocalizedMessage());
                }
            });
        }
    }
}
