package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.LoadingCallback;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendlessGoogleLogin.Defaults;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendlessGoogleLogin.LoginFragment;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.instgram.LoginInstagram;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.liknedIn.LoginLinkdin;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.WelcomeActivity;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LandLogin extends AppCompatActivity {

    LoginFragment loginFragment;
    public ProgressDialog progressDialog;

    ////// linked in declare Variable
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
    /////////////
    public static final String MY_PREFS_NAME = "Sawa7PrefsFile";
    boolean _faceOrnot=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_login);

        final LinearLayout linear1 = (LinearLayout) findViewById(R.id.Liner1);
        final LinearLayout linear2 = (LinearLayout) findViewById(R.id.Liner2);
        final LinearLayout linear3 = (LinearLayout) findViewById(R.id.Liner3);
        final LinearLayout linear4 = (LinearLayout) findViewById(R.id.Liner4);
        final LinearLayout linear5 = (LinearLayout) findViewById(R.id.Liner4);

        Button btnLandLoginFacebook = (Button) findViewById(R.id.btnLandLoginFacebook);
        Button btnLandLoginTwitter = (Button) findViewById(R.id.btnLandLoginTwitter);
        Button btnLandLoginLinkedin = (Button) findViewById(R.id.btnLandLoginLinkedin);
        Button btnLandLoginInstgram = (Button) findViewById(R.id.btnLandLoginInstgram);
        Button btnLandLoginGmailEmail = (Button) findViewById(R.id.btnLandLoginGimalEmail);

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        btnLandLoginFacebook.setOnClickListener(createLoginWithFacebookButtonListener());
        btnLandLoginTwitter.setOnClickListener(createLoginWithTwitterButtonListener());



        // create fragment for login/logout
        FragmentManager fm = this.getFragmentManager();
        loginFragment = (LoginFragment) fm.findFragmentByTag(LoginFragment.TAG);
        if (loginFragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            loginFragment = new LoginFragment();
            ft.add(loginFragment, LoginFragment.TAG);
            ft.commit();
        }

        btnLandLoginGmailEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragment.login_logout();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticate with Google+");
        progressDialog.setIndeterminate(true);

        btnLandLoginInstgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callActivity = new Intent(LandLogin.this, LoginInstagram.class);
                startActivity(callActivity);

            }
        });

        btnLandLoginLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_linkedin();
            }
        });
    }

    /**
     * Creates a listener, which proceeds with login with Facebook on button click.
     *
     * @return a listener, handling login with Facebook button click
     */
    public View.OnClickListener createLoginWithFacebookButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _faceOrnot=true;
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();
                loginCallback.showLoading();
                loginFacebookUser(loginCallback);
            }
        };
    }

    /**
     * Creates a listener, which proceeds with login with Twitter on button click.
     *
     * @return a listener, handling login with Facebook button click
     */
    public View.OnClickListener createLoginWithTwitterButtonListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                _faceOrnot=false;
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();
                loginCallback.showLoading();
                loginTwitterUser(loginCallback);
            }
        };
    }

    /**
     * Creates a callback, containing actions to be executed on login request result.
     * Shows a Toast with BackendlessUser's objectId on success,
     * show a dialog with an error message on failure.
     *
     * @return a callback, containing actions to be executed on login request result
     */
    public LoadingCallback<BackendlessUser> createLoginCallback() {
        return new LoadingCallback<BackendlessUser>(this, getString(R.string.loading_login)) {
            @Override
            public void handleResponse(BackendlessUser loggedInUser) {
                super.handleResponse(loggedInUser);
                Intent CalMain = new Intent(LandLogin.this, HomeActivity.class);

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String restoredText = prefs.getString("UserName", null);
                if (restoredText == null) {
                    CalMain = new Intent(LandLogin.this, WelcomeActivity.class);
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("UserName", loggedInUser.getEmail().toString());
                    editor.commit();
                }

                CalMain.putExtra("email", loggedInUser.getEmail().toString());
                CalMain.putExtra("name", loggedInUser.getProperty("name").toString());
                CalMain.putExtra("lastName", "");

                if(_faceOrnot) {
                    CalMain.putExtra("imageUrl", "https://graph.facebook.com/" + loggedInUser.getProperty("id").toString() + "/picture?type=large");
                }else {
                    CalMain.putExtra("imageUrl", "  https://twitter.com/"+loggedInUser.getProperty("name").toString()+"/profile_image?size=original \n");
                }
                startActivity(CalMain);

            }
        };
    }

    /**
     * Sends a request to Backendless to log in user with Facebook account.
     * Fetches Facebook user's name and saves it on Backendless.
     *
     * @param loginCallback a callback, containing actions to be executed on request result
     */
    public void loginFacebookUser(AsyncCallback<BackendlessUser> loginCallback) {
        Map<String, String> fieldsMappings = new HashMap<>();
        fieldsMappings.put("name", "name");
        Backendless.UserService.loginWithFacebook(this, null, fieldsMappings, Collections.<String>emptyList(), loginCallback);
    }

    /**
     * Sends a request to Backendless to log in user with Twitter account.
     * Fetches Twitter user's name and saves it on Backendless.
     *
     * @param loginCallback a callback, containing actions to be executed on request result
     */
    public void loginTwitterUser(AsyncCallback<BackendlessUser> loginCallback) {
        Map<String, String> fieldsMappings = new HashMap<>();
        fieldsMappings.put("name", "name");
        Backendless.UserService.loginWithTwitter(this, null, fieldsMappings, loginCallback);
    }


    // Authenticate with linkedin and intialize Session.
    public void login_linkedin() {
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                // login_linkedin_btn.setVisibility(View.GONE);
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
    public void generateHashkey() {
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

    public void getUserData() {
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(LandLogin.this, topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {
                    setUserProfile(result.getResponseDataAsJson());
                    // progress.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError error) {
                // ((TextView) findViewById(R.id.error)).setText(error.toString());

            }
        });
    }

    public void setUserProfile(JSONObject response) {
        try {
            email = response.get("emailAddress").toString();
            firstName = response.get("formattedName").toString();
            imageUrl = response.getString("pictureUrl").toString();
            nextActivityme();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nextActivityme() {
        Intent main = new Intent(LandLogin.this, HomeActivity.class);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("UserName", null);
        if (restoredText == null) {
            main = new Intent(LandLogin.this, WelcomeActivity.class);
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("UserName", email);
            editor.commit();
        }
        main.putExtra("MYAPI", 0);
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
