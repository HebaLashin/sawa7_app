package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.share.widget.ShareDialog;
import com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass.DownloadImage;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.LoginFacebook;
import com.uniquestartup.mohamedaliheeba.sawa7.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private ShareDialog mShareDialog;

    static String email;
    static String birthday;
    static String firstName;
    static String lastName;
    static String gender;
    static String location;
    static String idFacebook;
    static String acssesstokenFce;
    static String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle inBundle = getIntent().getExtras();
        //12345678910AERaer1491996
        email = inBundle.get("email").toString();
        // birthday = inBundle.get("birthday").toString();
        firstName = inBundle.get("firstName").toString();
        lastName = inBundle.get("lastName").toString();
        // gender = inBundle.get("gender").toString();
        // location = inBundle.get("location").toString();
        //  idFacebook = inBundle.get("idFacebook").toString();
        //  acssesstokenFce = inBundle.get("acssesstokenFce").toString();
        imageUrl = inBundle.get("imageUrl").toString();


        TextView nameView = (TextView) findViewById(R.id.nameAndSurname);

       // TextView Fname = (TextView) findViewById(R.id.P_F_name);
        //TextView LnameView = (TextView) findViewById(R.id.P_L_name);
        // TextView GnedernameView = (TextView)findViewById(R.id.P_Genter);
       // TextView EmailnameView = (TextView) findViewById(R.id.P_Email);
        // TextView LocationnameView = (TextView)findViewById(R.id.P_Location);
        // TextView birthView = (TextView)findViewById(R.id.P_birth);

        nameView.setText("" + firstName + " " + lastName);
      //  Fname.setText("First Name: " + firstName + " ");
        //LnameView.setText("Last name  :" + lastName);
        // GnedernameView.setText("Gender  :"+gender);
       // EmailnameView.setText(" Email :" + email);
        //LocationnameView.setText(" Location :"+location);
        // birthView.setText(" Birth Date :"+birthday);


//        mShareDialog = new ShareDialog(this);
        new DownloadImage((CircleImageView) findViewById(R.id.profileImage), ProfileActivity.this).execute(imageUrl);

    }

    public void logout() {
        LoginManager.getInstance().logOut();
        Intent login = new Intent(ProfileActivity.this, LoginFacebook.class);
        startActivity(login);
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
