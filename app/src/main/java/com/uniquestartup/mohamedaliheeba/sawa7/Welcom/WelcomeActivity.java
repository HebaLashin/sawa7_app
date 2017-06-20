package com.uniquestartup.mohamedaliheeba.sawa7.Welcom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.LoadingCallback;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.Validator;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.HomeActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.LandActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.LandLogin;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        // startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);
        createLoginButtonListener();

    }

    /**
     * Creates a listener, which proceeds with login by email and password on button click.
     *
     * @return a listener, handling login button click
     */
    public static final String EXTRA_USERNAME = "EMAIL";
    public static final String EXTRA_USERPASSWORD = "PASSWORD";

    public void createLoginButtonListener() {

        String USEREMAIL = getIntent().getStringExtra(WelcomeActivity.EXTRA_USERNAME);
        String USERPASSWORD = getIntent().getStringExtra(WelcomeActivity.EXTRA_USERPASSWORD);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int _meAPI = extras.getInt("MYAPI");
            if (_meAPI != 0) {
                CharSequence email = USEREMAIL;
                CharSequence password = USERPASSWORD;

                if (isLoginValuesValid(email, password)) {
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                    loginCallback.showLoading();
                    loginUser(email.toString(), password.toString(), loginCallback);
                }
            } else {
                Intent main = new Intent(WelcomeActivity.this, HomeActivity.class);

                main.putExtra("MYAPI", 0);
                main.putExtra("name", extras.getString("firstName"));
                main.putExtra("lastName", extras.getString("lastName"));
                if (extras.getString("imageUrl") != null && !extras.getString("imageUrl").isEmpty())
                    main.putExtra("imageUrl", extras.getString("imageUrl"));
                else
                    main.putExtra("imageUrl", "http://openplus.ca/images/photo.jpg");

                main.putExtra("email", extras.getString("email"));
                startActivity(main);

            }
        }
    }

    public void loginUser(String email, String password, AsyncCallback<BackendlessUser> loginCallback) {
        Backendless.UserService.login(email, password, loginCallback);
    }

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
    public LoadingCallback<BackendlessUser> createLoginCallback() {
        return new LoadingCallback<BackendlessUser>(this, getString(R.string.loading_login)) {
            @Override
            public void handleResponse(BackendlessUser loggedInUser) {
                super.handleResponse(loggedInUser);
                // Toast.makeText( LoginActivity.this, String.format( getString( R.string.info_logged_in ), loggedInUser.getObjectId() ), Toast.LENGTH_LONG ).show();
                Intent CalMain = new Intent(WelcomeActivity.this, HomeActivity.class);
                CalMain.putExtra("email", loggedInUser.getEmail().toString());
                CalMain.putExtra("name", loggedInUser.getProperty("name").toString());
                CalMain.putExtra("lastName", "");
                CalMain.putExtra("imageUrl", "http://openplus.ca/images/photo.jpg");

                startActivity(CalMain);

            }
        };
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
