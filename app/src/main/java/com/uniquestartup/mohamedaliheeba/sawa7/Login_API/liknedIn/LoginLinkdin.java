package com.uniquestartup.mohamedaliheeba.sawa7.Login_API.liknedIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.squareup.picasso.Picasso;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.LoginFacebook;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.ProfileActivity;

import org.json.JSONObject;


public class LoginLinkdin extends AppCompatActivity {


    private static final String TAG = LoginLinkdin.class.getSimpleName();
    public static final String PACKAGE = "com.uniquestartup.mohamedaliheeba.sawa7";
    private static final String host = "api.linkedin.com";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
    ProgressDialog progress;
    Button login_linkedin_btn;
    static String email = " ";
    static String birthday = " ";
    static String firstName = " ";
    static String lastName = " ";
    static String gender = " ";
    static String location = " ";
    static String idFacebook = " ";
    static String acssesstokenFce = " ";
    static String imageUrl = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_linkdin);


        login_linkedin_btn = (Button) findViewById(R.id.login_button);
        login_linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_linkedin();
            }
        });


    }

    // Authenticate with linkedin and intialize Session.

    public void login_linkedin(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                // Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                login_linkedin_btn.setVisibility(View.GONE);

            }

            @Override
            public void onAuthError(LIAuthError error) {

                Toast.makeText(getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    // After complete authentication start new HomePage Activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    //    Intent intent = new Intent(LoginLinkdin.this,UserProfile.class);
//        startActivity(intent);
        getUserData();
    }

    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    // This Method is used to generate "Android Package Name" hash key

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                //((TextView) findViewById(R.id.package_name)).setText(info.packageName);
               //((TextView) findViewById(R.id.hash_key)).setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public void getUserData(){
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(LoginLinkdin.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    setUserProfile(result.getResponseDataAsJson());
                   // progress.dismiss();

                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError error) {
                // ((TextView) findViewById(R.id.error)).setText(error.toString());

            }
        });
    }

    public  void  setUserProfile(JSONObject response){

        try {

           email= response.get("emailAddress").toString();
            firstName= response.get("formattedName").toString();
            imageUrl=response.getString("pictureUrl").toString();

            nextActivityme();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void nextActivityme() {


        Intent main = new Intent(LoginLinkdin.this, HomeActivity.class);

        main.putExtra("name", firstName);
        main.putExtra("lastName", lastName);
        main.putExtra("imageUrl", imageUrl);
        main.putExtra("idFacebook", idFacebook);
        main.putExtra("gender", gender);
        main.putExtra("acssesstokenFce", acssesstokenFce);
        main.putExtra("email", email);
        main.putExtra("birthday", birthday);
        main.putExtra("location", location);

        startActivity(main);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
