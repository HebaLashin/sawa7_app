package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.LoadingCallback;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.Validator;
import com.uniquestartup.mohamedaliheeba.sawa7.CoreApp.TranslateActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.uz.efir.muazzin.Muazzin;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.LoginFacebook;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.liknedIn.LoginLinkdin;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.LoginTwiter;
import com.uniquestartup.mohamedaliheeba.sawa7.Login_API.instgram.LoginInstagram;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.WelcomeActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REGISTER_REQUEST_CODE = 1;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    ImageButton _twitterBtn;
    ImageButton _facebookBtn;
    ImageButton _instagramBtn;
    ImageButton _linkedinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Latobold.ttf");

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        _emailText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        _passwordText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        // _twitterBtn = (ImageButton) findViewById(R.id.btnTwitter);
        // _facebookBtn = (ImageButton) findViewById(R.id.btnfacebook);
        // _instagramBtn = (ImageButton) findViewById(R.id.btnInstagram);
        //  _linkedinBtn = (ImageButton) findViewById(R.id.btnLinkedin);

        _emailText.setTypeface(typeface);
        _passwordText.setTypeface(typeface);
        _loginButton.setTypeface(typeface);
        _signupLink.setTypeface(typeface);

        _emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                _emailText.setHintTextColor(getResources().getColor(R.color.white));
            }
        });


        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(createLoginButtonListener());

        // Button loginFacebookButton = (Button) findViewById( R.id.loginFacebookButton );
        //   loginFacebookButton.setOnClickListener( createLoginWithFacebookButtonListener() );

        //  Button loginTwitterButton = (Button) findViewById( R.id.loginTwitterButton );
        //  loginTwitterButton.setOnClickListener( createLoginWithTwitterButtonListener() );

        //  makeRegistrationLink();

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        // moveTaskToBack(true);
        finish();
        Intent main = new Intent(LoginActivity.this, LandActivity.class);
        startActivity(main);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
        Intent main = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(main);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("لا يمكنك ترك هذا فارغًا");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("أدخل عنوان بريد إلكتروني صالح");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("لا يمكنك ترك هذا فارغًا");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    ////////////////////////////////////

    /**
     * Sends a request for registration to RegistrationActivity,
     * expects for result in onActivityResult.
     */
    public void startRegistrationActivity() {
        Intent registrationIntent = new Intent(this, SignupActivity.class);
        startActivityForResult(registrationIntent, REGISTER_REQUEST_CODE);
    }

    /**
     * Sends a request to Backendless to log in user by email and password.
     *
     * @param email         user's email
     * @param password      user's password
     * @param loginCallback a callback, containing actions to be executed on request result
     */
    public void loginUser(String email, String password, AsyncCallback<BackendlessUser> loginCallback) {
        Backendless.UserService.login(email, password, loginCallback);
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

    /**
     * Creates a listener, which proceeds with login by email and password on button click.
     *
     * @return a listener, handling login button click
     */
    public View.OnClickListener createLoginButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField = (EditText) findViewById(R.id.input_email);
                EditText passwordField = (EditText) findViewById(R.id.input_password);

                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();

                if (isLoginValuesValid(email, password)) {
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                    loginCallback.showLoading();
                    loginUser(email.toString(), password.toString(), loginCallback);
                }
            }
        };
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
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                loginCallback.showLoading();
                loginTwitterUser(loginCallback);
            }
        };
    }

    /**
     * Validates the values, which user entered on login screen.
     * Shows Toast with a warning if something is wrong.
     *
     * @param email    user's email
     * @param password user's password
     * @return true if all values are OK, false if something is wrong
     */
    public boolean isLoginValuesValid(CharSequence email, CharSequence password) {
        return Validator.isEmailValid(this, email) && Validator.isPasswordValid(this, password);
    }

    /**
     * Creates a callback, containing actions to be executed on login request result.
     * Shows a Toast with BackendlessUser's objectId on success,
     * show a dialog with an error message on failure.
     *
     * @return a callback, containing actions to be executed on login request result
     */
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public LoadingCallback<BackendlessUser> createLoginCallback() {
        return new LoadingCallback<BackendlessUser>(this, getString(R.string.loading_login)) {
            @Override
            public void handleResponse(BackendlessUser loggedInUser) {
                super.handleResponse(loggedInUser);

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String restoredText = prefs.getString("UserName", null);


                // Toast.makeText( LoginActivity.this, String.format( getString( R.string.info_logged_in ), loggedInUser.getObjectId() ), Toast.LENGTH_LONG ).show();
                Intent CalMain = new Intent(LoginActivity.this, HomeActivity.class);
                CalMain.putExtra("UserName", loggedInUser.getEmail().toString());
                CalMain.putExtra("email", loggedInUser.getEmail().toString());
                CalMain.putExtra("name", loggedInUser.getProperty("name").toString());
                CalMain.putExtra("lastName", "");
                CalMain.putExtra("imageUrl", "http://openplus.ca/images/photo.jpg");

                if (restoredText == null) {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("UserName", loggedInUser.getEmail().toString());
                    editor.commit();
                }

                startActivity(CalMain);

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REGISTER_REQUEST_CODE:
                    String email = data.getStringExtra(BackendlessUser.EMAIL_KEY);
                    EditText emailField = (EditText) findViewById(R.id.input_email);
                    emailField.setText(email);

                    EditText passwordField = (EditText) findViewById(R.id.input_password);
                    passwordField.requestFocus();

                    Toast.makeText(this, getString(R.string.info_registered_success), Toast.LENGTH_SHORT).show();
            }
        }
    }
}