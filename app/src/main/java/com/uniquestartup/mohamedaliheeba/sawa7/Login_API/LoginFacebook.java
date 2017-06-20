package com.uniquestartup.mohamedaliheeba.sawa7.Login_API;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.ProfileActivity;
import org.json.JSONObject;
import java.net.URL;
import java.util.Arrays;

public class LoginFacebook extends AppCompatActivity {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    static String email = " ";
    static String birthday = " ";
    static String firstName = " ";
    static String lastName = " ";
    static String gender = " ";
    static String location = " ";
    static String idFacebook = " ";
    static String acssesstokenFce = " ";
    static String imageUrl = " ";

    static Profile profile;
    ProgressDialog progressDialog;
    //Facebook login button
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            nextActivity(profile);
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);

        /////////////////// facebook login //////////////////////////
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                acssesstokenFce = loginResult.getAccessToken().getToken();
                profile = Profile.getCurrentProfile();
                progressDialog = new ProgressDialog(LoginFacebook.this);
                progressDialog.setMessage("Plz waite...");
                progressDialog.show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginFacebook", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);


                    }

                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends", "user_location"));
        loginButton.registerCallback(callbackManager, callback);


        ////////////////////////// End /////////////////////////////////
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {

            String id = object.getString("id");
            idFacebook = id;

            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=200");
            Log.i("profile_pic", profile_pic + "");
            bundle.putString("profile_pic", profile_pic.toString());
            imageUrl = profile_pic.toString();
            bundle.putString("idFacebook", id);
            if (object.has("first_name")) {
                bundle.putString("first_name", object.getString("first_name"));
                firstName = object.getString("first_name");
            }
            if (object.has("last_name")) {
                bundle.putString("last_name", object.getString("last_name"));
                lastName = object.getString("last_name");
            }

            if (object.has("gender")) {
                bundle.putString("gender", object.getString("gender"));
                gender = object.getString("gender");
            }

            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
                email = object.getString("email");
            }

            if (object.has("birthday")) {
                bundle.putString("birthday", object.getString("birthday"));
                birthday = object.getString("birthday");
            }
            if (object.has("location")) {
                bundle.putString("location", object.getJSONObject("location").getString("name"));
                location = object.getJSONObject("location").getString("name");
            }
            progressDialog.hide();
            nextActivityme();
            Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bundle;


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }


    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    private void nextActivity(Profile profile) {

        if (profile != null) {
            Intent main = new Intent(LoginFacebook.this, HomeActivity.class);

            main.putExtra("name", profile.getFirstName());
            main.putExtra("lastName", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200, 200));
            main.putExtra("idFacebook", profile.getId());
            main.putExtra("gender", gender);
            main.putExtra("acssesstokenFce", acssesstokenFce);
            main.putExtra("email", email);
            main.putExtra("birthday", birthday);
            main.putExtra("location", location);

            startActivity(main);
        }

    }

    private void nextActivityme() {


        Intent main = new Intent(LoginFacebook.this, ProfileActivity.class);

        main.putExtra("firstName", firstName);
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
