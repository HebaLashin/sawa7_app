package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_adapters.DisplayAdapter;

import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_models.Currency;

public class DisplayCurrencyActivity extends ActionBarActivity {

    private DisplayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_display);

        TextView mDisplayView = (TextView) findViewById(R.id.text_display_currency);
        ListView mDisplayList = (ListView) findViewById(R.id.list_display_currencies);
        SearchView mDisplaySearch = (SearchView) findViewById(R.id.search_display);

        Currency currency = getIntent().getParcelableExtra(CurrencyActivity.KEY_CURRENCY);
        double rate = currency.getRate();
        double amount = getIntent().getDoubleExtra(CurrencyActivity.KEY_AMOUNT, -1.0);

        mDisplayView.setText(amount + " " + currency.getCode() + "(" + currency.getName() + ") is: ");

        mAdapter = new DisplayAdapter(this, rate, amount);

        mDisplayList.setAdapter(mAdapter);

        mDisplaySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.updateAdapter(newText);
                return true;
            }
        });

        mDisplaySearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    mAdapter.resetAdapter();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
