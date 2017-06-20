package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.R;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText _emailText;
    Button btnResetpassword;
    private ProgressDialog mLoginProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        _emailText = (EditText) findViewById(R.id.editResetEmail);
        btnResetpassword = (Button) findViewById(R.id.btn_resetPassword);
        btnResetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();


        if (email.isEmpty()) {
            _emailText.setError("this filed is Required ");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Invalid Email Format ");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        return valid;
    }

    public void login() {
        //Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        btnResetpassword.setEnabled(false);

        mLoginProgressDialog = new ProgressDialog(ResetPasswordActivity.this);
        mLoginProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoginProgressDialog.setMessage("Send Email With new Password ...");
        mLoginProgressDialog.setCancelable(false);
        mLoginProgressDialog.setCanceledOnTouchOutside(false);
        mLoginProgressDialog.show();

        Backendless.UserService.restorePassword(_emailText.getText().toString().trim(), new AsyncCallback<Void>() {
            public void handleResponse(Void response) {
                // Backendless has completed the operation - an email has been sent to the user

                onLoginSuccess();
            }

            public void handleFault(BackendlessFault fault) {
                // password revovery failed, to get the error code call fault.getCode()
            }
        });
    }

    public void onLoginSuccess() {
        // _loginButton.setEnabled(true);
        mLoginProgressDialog.hide();
        finish();
        Intent main = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(main);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "I can't send reset password email", Toast.LENGTH_LONG).show();

        //  _loginButton.setEnabled(true);
    }

}
