package com.uniquestartup.mohamedaliheeba.sawa7.Login_API;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.ProfileActivity;

import io.fabric.sdk.android.Fabric;

public class LoginTwiter extends AppCompatActivity {

    private static final String TWITTER_KEY = "DVGsGMrctnDWpgsYBL52hPiFH";
    private static final String TWITTER_SECRET = "gGwzU2zWSck5xDJA4jYjzl6hY1ZZejcyb0ctbNu64nfhGCzG2w";

    Long userid;
    TwitterSession session;
    private TwitterLoginButton loginButton;
    TextView textView;

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

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login_twiter);

        textView = (TextView) findViewById(R.id.tv_username);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()

                Log.d("Twitter ", "Login sucessfull");
                session = result.data;

                String username = session.getUserName();
                userid = session.getUserId();


                textView.setText("Hi " + username);
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model

                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                getUserData();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);


    }

    void getUserData() {
        Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void failure(TwitterException e) {

            }

            @Override
            public void success(Result<User> userResult) {

                User user = userResult.data;
                String twitterImage = user.profileImageUrl;
                try {

                           /* Log.d("imageurl", user.profileImageUrl);
                            Log.d("name", user.name);
                            Log.d("email",user.email);
                            Log.d("des", user.description);
                            Log.d("followers ", String.valueOf(user.followersCount));
                            Log.d("createdAt", user.createdAt);*/
                    Intent main = new Intent(LoginTwiter.this, HomeActivity.class);

                    main.putExtra("name", user.name);
                    main.putExtra("lastName", user.name);
                    main.putExtra("imageUrl", user.profileImageUrl);
                    main.putExtra("idFacebook", idFacebook);
                    main.putExtra("gender", String.valueOf(user.followersCount));
                    main.putExtra("acssesstokenFce", acssesstokenFce);
                    main.putExtra("email", email);
                    main.putExtra("birthday", user.createdAt);
                    main.putExtra("location", user.description);

                    startActivity(main);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
