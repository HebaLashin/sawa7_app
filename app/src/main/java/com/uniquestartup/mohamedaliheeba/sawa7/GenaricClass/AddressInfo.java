package com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Moham on 02/07/2016.
 */

public class AddressInfo {


    public static Address getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        Address result=null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            }
            return null;
        } catch (IOException ignored) {
            //do something
            return result;
        }

    }
}
