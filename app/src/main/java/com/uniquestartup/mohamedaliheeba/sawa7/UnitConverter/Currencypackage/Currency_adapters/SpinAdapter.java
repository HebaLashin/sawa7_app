package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_models.Currency;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.WeatherPackage.model.Weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Moham on 30/07/2016.
 */
public class SpinAdapter extends ArrayAdapter<Currency> implements SectionIndexer {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private Currency[] values;
    int layoutResourceId;

    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;


    public SpinAdapter(Context context, int textViewResourceId, Currency[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.layoutResourceId = textViewResourceId;
        this.values = values;
        ArrayList<Currency> list = new ArrayList<Currency>(Arrays.asList(values));
        alphaIndexer = new HashMap<String, Integer>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getName().substring(0, 1).toUpperCase();
            if (!alphaIndexer.containsKey(s))
                alphaIndexer.put(s, i);
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        for (int i = 0; i < sectionList.size(); i++)
            sections[i] = sectionList.get(i);
    }

    public int getCount() {
        return values.length;
    }

    public Currency getItem(int position) {
        return values[position];
    }

    public long getItemId(int position) {
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CurrencyHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CurrencyHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            holder.txtCode = (TextView) row.findViewById(R.id.txtCode);

            row.setTag(holder);
        } else {
            holder = (CurrencyHolder) row.getTag();
        }

        Currency currencyobj = values[position];
        holder.txtTitle.setText(currencyobj.getName());
        holder.txtCode.setText(currencyobj.getCode());


        return row;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CurrencyHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CurrencyHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            holder.txtCode = (TextView) row.findViewById(R.id.txtCode);

            row.setTag(holder);
        } else {
            holder = (CurrencyHolder) row.getTag();
        }

        Currency currencyobj = values[position];
        holder.txtTitle.setText(currencyobj.getName());
        holder.txtCode.setText(currencyobj.getCode());


        return row;
    }

    public int getPositionForSection(int section) {
        return alphaIndexer.get(sections[section]);
    }

    public int getSectionForPosition(int position) {
        return 1;
    }

    public Object[] getSections() {
        return sections;
    }

    static class CurrencyHolder {
        TextView txtTitle;
        TextView txtCode;
    }
}