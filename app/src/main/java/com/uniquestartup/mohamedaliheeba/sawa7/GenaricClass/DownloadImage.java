package com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Context context;
    private ProgressDialog mLoginProgressDialog;
    public DownloadImage(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context=context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPreExecute() {
        mLoginProgressDialog = new ProgressDialog(context);
        mLoginProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoginProgressDialog.setMessage("Loading all the data ...");
        mLoginProgressDialog.setCancelable(false);
        mLoginProgressDialog.setCanceledOnTouchOutside(false);
        mLoginProgressDialog.show();
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        mLoginProgressDialog.hide();
    }
}