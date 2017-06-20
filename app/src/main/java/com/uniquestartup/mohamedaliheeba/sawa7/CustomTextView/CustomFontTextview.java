package com.uniquestartup.mohamedaliheeba.sawa7.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Moham on 15/07/2016.
 */

public class CustomFontTextview extends TextView
{
    public CustomFontTextview(Context context)
    {
        super(context);
        init();
    }

    public CustomFontTextview(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomFontTextview(Context context, AttributeSet attrs, int defStyle)
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
