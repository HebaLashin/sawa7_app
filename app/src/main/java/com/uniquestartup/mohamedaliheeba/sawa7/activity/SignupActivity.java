package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;

import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.LoadingCallback;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.Validator;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.PrefManager;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.WelcomeActivity;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText _nameText;
    EditText _emailText;
    EditText _passwordText, _passwordConfirmText;
    Button _signupButton;
    TextView _loginLink;
    EditText emailFieldex;
    EditText passwordFieldex;
    public static final String MY_PREFS_NAME = "Sawa7PrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Latobold.ttf");

        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);


        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _nameText.setTypeface(typeface);
        _emailText.setTypeface(typeface);
        _passwordText.setTypeface(typeface);
        _signupButton.setTypeface(typeface);
        _loginLink.setTypeface(typeface);

        _nameText.getBackground().setColorFilter(Color.parseColor("white"), PorterDuff.Mode.SRC_IN);
        _emailText.getBackground().setColorFilter(Color.parseColor("white"), PorterDuff.Mode.SRC_IN);
        _passwordText.getBackground().setColorFilter(Color.parseColor("white"), PorterDuff.Mode.SRC_IN);

        emailFieldex = (EditText) findViewById(R.id.input_email);
        passwordFieldex = (EditText) findViewById(R.id.input_password);

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                //  finish();
            }
        });

        Button registerButton = (Button) findViewById(R.id.btn_signup);

        View.OnClickListener registerButtonClickListener = createRegisterButtonClickListener();

        registerButton.setOnClickListener(registerButtonClickListener);

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("At least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty()) {
            _emailText.setError("You can not leave this empty");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid e-mail address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("You can not leave this empty");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (password.length() < 8 && password.length() > 30) {
            _passwordText.setError("Short passwords are easy to guess. Retry using 8 characters at least.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }


        return valid;
    }

    /**
     * Validates the values, which user entered on registration screen.
     * Shows Toast with a warning if something is wrong.
     *
     * @param name            user's name
     * @param email           user's email
     * @param password        user's password
     * @param passwordConfirm user's password confirmation
     * @return true if all values are OK, false if something is wrong
     */
    public boolean isRegistrationValuesValid(CharSequence name, CharSequence email, CharSequence password,
                                             CharSequence passwordConfirm) {
        return Validator.isNameValid(this, name)
                && Validator.isEmailValid(this, email) &&
                Validator.isPasswordValid(this, password)
                && isPasswordsMatch(password, passwordConfirm);
    }

    /**
     * Determines whether password and password confirmation are the same.
     * Displays Toast with a warning if not.
     *
     * @param password        password
     * @param passwordConfirm password confirmation
     * @return true if password and password confirmation match, else false
     */
    public boolean isPasswordsMatch(CharSequence password, CharSequence passwordConfirm) {
        if (!TextUtils.equals(password, passwordConfirm)) {
            Toast.makeText(this, getString(R.string.warning_passwords_do_not_match), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Sends a request to Backendless to register user.
     *
     * @param name                 user's name
     * @param email                user's email
     * @param password             user's password
     * @param registrationCallback a callback, containing actions to be executed on request result
     */
    public void registerUser(String name, String email, String password, AsyncCallback<BackendlessUser> registrationCallback) {
        BackendlessUser user = new BackendlessUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setProperty("name", name);

        //Backendless handles password hashing by itself, so we don't need to send hash instead of plain text
        Backendless.UserService.register(user, registrationCallback);
    }

    /**
     * Creates a callback, containing actions to be executed on registration request result.
     * Sends result intent containing registered user's email to calling activity on success.
     *
     * @return a callback, containing actions to be executed on registration request result
     */
    public static final String EXTRA_USERNAME = "EMAIL";
    public static final String EXTRA_USERPASSWORD = "PASSWORD";
    private static final int REQUEST_RESPONSE = 1;

    public LoadingCallback<BackendlessUser> createRegistrationCallback() {
        return new LoadingCallback<BackendlessUser>(this, getString(R.string.loading_register)) {
            @Override
            public void handleResponse(BackendlessUser registeredUser) {
                super.handleResponse(registeredUser);
                Intent registrationResult = new Intent();
                registrationResult.putExtra(BackendlessUser.EMAIL_KEY, registeredUser.getEmail());
                setResult(RESULT_OK, registrationResult);
                //SignupActivity.this.finish();
                /*Intent cal=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(cal);*/
                PrefManager prefManager = new PrefManager(getApplicationContext());

                // make first time launch TRUE
                prefManager.setFirstTimeLaunch(true);


                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("UserName", emailFieldex.getText().toString());
                editor.commit();

                Intent callActivity = new Intent(SignupActivity.this, WelcomeActivity.class);
                callActivity.putExtra(EXTRA_USERNAME, emailFieldex.getText().toString());
                callActivity.putExtra(EXTRA_USERPASSWORD, passwordFieldex.getText().toString());
                callActivity.putExtra("MYAPI", 1);
                startActivityForResult(callActivity, REQUEST_RESPONSE);
                finish();
            }
        };
    }

    /**
     * Creates a listener, which proceeds with registration on button click.
     *
     * @return a listener, handling registration button click
     */
    public View.OnClickListener createRegisterButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameField = (EditText) findViewById(R.id.input_name);
                EditText emailField = (EditText) findViewById(R.id.input_email);
                EditText passwordField = (EditText) findViewById(R.id.input_password);
                CharSequence name = nameField.getText();
                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();


                if (!validate()) {
                    onSignupFailed();
                    return;
                } else {
                    LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();
                    registrationCallback.showLoading();
                    registerUser(name.toString(), email.toString(), password.toString(), registrationCallback);
                }
            }
        };
    }
}