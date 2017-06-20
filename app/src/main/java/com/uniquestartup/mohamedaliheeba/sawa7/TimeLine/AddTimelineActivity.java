package com.uniquestartup.mohamedaliheeba.sawa7.TimeLine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTimelineActivity extends AppCompatActivity {
    TimelineObject fileMapping = new TimelineObject();
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    static Bitmap photo;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timeline);

        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        this.imageView = (ImageView) this.findViewById(R.id.image_place);
        Button photoButton = (Button) this.findViewById(R.id.btnOpencam);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Button showTimeline = (Button) this.findViewById(R.id.btnupShowTimeline);
        showTimeline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent showtimeline = new Intent(AddTimelineActivity.this,TimelineActivity.class);
                startActivity(showtimeline);
            }
        });

        Button photoButton2 = (Button) this.findViewById(R.id.btnChooseFile);
        photoButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        Button upload = (Button) this.findViewById(R.id.btnupload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Backendless.Files.Android.upload(photo, Bitmap.CompressFormat.PNG, 100,  Calendar.getInstance().getTimeInMillis()+",a,me,ciro,egypt.png", "TimeLineImage", new AsyncCallback<BackendlessFile>() {
                    @Override
                    public void handleResponse(BackendlessFile response) {
                        TimelineObject _timeline = new TimelineObject();
                        _timeline.setTimeline_image_url(response.getFileURL());
                        _timeline.setUser_name("me");
                        _timeline.setUser_email("a@a.com");
                        _timeline.setDescription_area("ws");
                        _timeline.setCity("ws");
                        _timeline.setCountry("ws");
                        Backendless.Data.of(TimelineObject.class).save(_timeline, new AsyncCallback<TimelineObject>() {
                            @Override
                            public void handleResponse(TimelineObject response) {

                                toastShow("add post success ^_^");
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                String _message = fault.getMessage();
                                toastShow(_message);
                            }
                        });

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        String _message = fault.getMessage();
                        toastShow(_message);
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        try {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && null != data) {
                photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                toastShow("Camera was cancelled by user...");
            }

            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.image_place);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                imageView.buildDrawingCache();
                photo = imageView.getDrawingCache();

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void toastShow(String _message) {
        Toast.makeText(this, _message, Toast.LENGTH_LONG).show();
    }


}
