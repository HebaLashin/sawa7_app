package com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.Currencypackage.Currency_models;

import java.util.HashMap;

/**
 * @author Nikhil Peter Raj
 */
public class RateResponse {

    private long timestamp;
    private HashMap<String, Double> rates;

    public long getTimestamp() {
        return timestamp;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }
}
