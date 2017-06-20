package com.uniquestartup.mohamedaliheeba.sawa7.BackendlessGoogleLogin;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.WelcomeActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.LandLogin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "LoginFragment";
    private static final int RC_SIGN_IN = 10001;
    private static final int RC_SIGN_OUT = 10002;
    private static final int REQUEST_AUTHORIZATION = 10003;
    private boolean logined = false;
    private static final String SERVER_CLIENT_ID = "725872759898-hu9j8l567nbb5huuqqgothfismqjds7f.apps.googleusercontent.com";

    private GoogleSignInOptions gSignInOptions;
    private GoogleApiClient gApiClient;
    public static final String MY_PREFS_NAME = "Sawa7PrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity fragmentActivity = (FragmentActivity) this.getActivity();

        gSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile().requestId().requestIdToken(SERVER_CLIENT_ID)
                .build();

        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        GoogleApiClient.Builder apiCliBuilder = new GoogleApiClient.Builder(fragmentActivity);
        gApiClient = apiCliBuilder
                .enableAutoManage(fragmentActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gSignInOptions).build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    public void login_logout() {
        showProgressDialog();
        if (logined) {
            logoutFromBackendless();
            Auth.GoogleSignInApi.signOut(gApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    handleSignOutResult(status);
                }
            });
        } else {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(gApiClient);
            this.startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult: success is " + result.isSuccess());

        if (result.isSuccess()) {
            logined = true;
            refreshUIonLogin(this.getActivity(), result.getSignInAccount());
            loginInBackendless(result.getSignInAccount());
        } else {
            logined = false;
            refreshUIonLogout();
            // Signed out, show unauthenticated UI.
        }
        hideProgressDialog();
    }

    private void handleSignOutResult(Status status) {
        logined = false;
        refreshUIonLogout();
        hideProgressDialog();
    }

    private void loginInBackendless(final GoogleSignInAccount acct) {
        Log.d(TAG, "handleSignInResult: try login to backendless");

        final LandLogin mainActivity = (LandLogin) this.getActivity();
        final String accountName = acct.getEmail();
        final String scopes = "oauth2:" + Scopes.PLUS_LOGIN + " " + Scopes.PLUS_ME + " " + Scopes.PROFILE + " " + Scopes.EMAIL;

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                try {
                    token = GoogleAuthUtil.getToken(mainActivity, accountName, scopes);
                    GoogleAuthUtil.invalidateToken(mainActivity, token);
                    handleAccessTokenInBackendless(acct.getIdToken(), token);
                } catch (UserRecoverableAuthException e) {
                    startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return token;
            }
        };

        task.execute();
    }

    private void handleAccessTokenInBackendless(String idToken, String accessToken) {
        Log.d(TAG, "idToken: " + idToken + ", accessToken: " + accessToken);

        Map<String, String> googlePlusFieldsMapping = new HashMap<String, String>();
        googlePlusFieldsMapping.put("given_name", "gp_given_name");
        googlePlusFieldsMapping.put("family_name", "gp_family_name");
        googlePlusFieldsMapping.put("gender", "gender");
        googlePlusFieldsMapping.put("email", "email");
        List<String> permissions = new ArrayList<String>();

        if (idToken != null && accessToken != null)
            Backendless.UserService.loginWithGooglePlusSdk(idToken, accessToken, googlePlusFieldsMapping, permissions,
                    new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser backendlessUser) {
                            Log.i(TAG, "Logged in to backendless, user id is: " + backendlessUser.getObjectId());
                            refreshUIonLoginBackendless(LoginFragment.this.getActivity(), backendlessUser);
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Log.e(TAG, "Could not login to backendless: " + backendlessFault.getMessage() + " code: " + backendlessFault.getCode());
                        }
                    });
    }

    private void logoutFromBackendless() {
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void aVoid) {
                Log.i(TAG, "Logout from backendless");
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Log.i(TAG, "Error when logout from backendless: " + backendlessFault.getMessage() + ". Code: " + backendlessFault.getCode());
            }
        });
    }

    private void refreshUIonLogin(Context ctx, GoogleSignInAccount acct) {
        LandLogin activity = (LandLogin) ctx;
        Intent CalMain = new Intent(activity, HomeActivity.class);
        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, activity.MODE_PRIVATE);
        String restoredText = prefs.getString("UserName", null);
        if (restoredText == null) {
            CalMain = new Intent(activity, WelcomeActivity.class);
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, activity.MODE_PRIVATE).edit();
            editor.putString("UserName", acct.getEmail());
            editor.commit();
        }
        CalMain.putExtra("email", acct.getEmail());
        CalMain.putExtra("name", acct.getDisplayName());
        CalMain.putExtra("lastName", "");

        if(!acct.getPhotoUrl().toString().isEmpty())
            CalMain.putExtra("imageUrl", acct.getPhotoUrl());
        else
            CalMain.putExtra("imageUrl", "http://openplus.ca/images/photo.jpg");

        startActivity(CalMain);

        String scopes = Arrays.toString(acct.getGrantedScopes().toArray());
        // arrayAdapter.add( "Scopes: "+ scopes);
        //  arrayAdapter.add( "idToken: "+ acct.getIdToken());
        //arrayAdapter.notifyDataSetChanged();
    }

    private void refreshUIonLoginBackendless(Context ctx, BackendlessUser user) {
        LandLogin activity = (LandLogin) ctx;
       /* ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>)activity.listView.getAdapter();

        arrayAdapter.add( "Backendless user objectId: " + user.getObjectId() );
        arrayAdapter.add( "Backendless user userId: " + user.getUserId() );
        arrayAdapter.add( "Backendless user email: " + user.getEmail() );
        arrayAdapter.add( "Backendless user name: " + user.getProperty("name") );
        arrayAdapter.add( "Backendless user password: " + user.getPassword() );*/
    }

    private void refreshUIonLogout() {
       /* MainActivity mainActivity = (MainActivity)this.getActivity();
        ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>)mainActivity.listView.getAdapter();

        mainActivity.textView.setText( R.string.text_loginStatus_out );
        mainActivity.googleButton.setText( R.string.button_login );
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();*/
    }

    private void showProgressDialog() {
        LandLogin mainActivity = (LandLogin) this.getActivity();
        mainActivity.progressDialog.show();
    }

    private void hideProgressDialog() {
        LandLogin mainActivity = (LandLogin) this.getActivity();
        if (mainActivity.progressDialog.isShowing())
            mainActivity.progressDialog.hide();
    }
}
