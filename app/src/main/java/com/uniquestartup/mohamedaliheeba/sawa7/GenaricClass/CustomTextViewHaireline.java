package com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Moham on 24/09/2016.
 */

public class CustomTextViewHaireline extends TextView
{
    public CustomTextViewHaireline(Context context)
    {
        super(context);
        init();
    }

    public CustomTextViewHaireline(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomTextViewHaireline(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void init()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Latobold.ttf");
        this.setTypeface(tf);
    }

}