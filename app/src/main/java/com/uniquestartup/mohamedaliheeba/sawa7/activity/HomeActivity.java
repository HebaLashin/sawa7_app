package com.uniquestartup.mohamedaliheeba.sawa7.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uniquestartup.mohamedaliheeba.sawa7.CityGuide.City_GuideActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.CoreApp.TranslateActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.ElQuiblaNow.QuiblaCollection;
import com.uniquestartup.mohamedaliheeba.sawa7.Elquibla.uz.efir.muazzin.Muazzin;
import com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass.DownloadImage;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.Translation.TranslationPage;
import com.uniquestartup.mohamedaliheeba.sawa7.UnitConverter.UniteConverterActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.Welcom.WelcomeActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imgConvert, imgElquibla, imgTranslation, imgcurrentloaction;

    static String email;
    static String birthday;
    static String firstName;
    static String lastName;
    static String gender;
    static String location;
    static String idFacebook;
    static String acssesstokenFce;
    static String imageUrl;

    CircleImageView _profileImage;

    LocationManager locationManager;
    double longitudeNetwork, latitudeNetwork;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("UserName", null);
        if (restoredText == null) {
            Intent CalMain = new Intent(HomeActivity.this, WelcomeActivity.class);
            startActivity(CalMain);
        }

        setContentView(R.layout.activity_home);
        // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Latobold.ttf");
        Typeface typefaceHeader = Typeface.createFromAsset(getAssets(), "fonts/helveticalight.ttf");
        Typeface typefaceItemText = Typeface.createFromAsset(getAssets(), "fonts/myriad.otf");

        TextView txtTimeLine = (TextView) findViewById(R.id.txtTimeLine);
        TextView txtElquibla = (TextView) findViewById(R.id.txtElquibla);
        TextView txtTranslation = (TextView) findViewById(R.id.txtTranslation);
        TextView txtActivityFinder = (TextView) findViewById(R.id.txtActivityFinder);
        TextView txtConverstion = (TextView) findViewById(R.id.txtConverstion);

        txtTimeLine.setTypeface(typefaceItemText);
        txtElquibla.setTypeface(typefaceItemText);
        txtTranslation.setTypeface(typefaceItemText);
        txtActivityFinder.setTypeface(typefaceItemText);
        txtConverstion.setTypeface(typefaceItemText);


        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        // toggle.syncState();

        Button btnNavigation = (Button) findViewById(R.id.btnNavigation);
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    // close
                    drawer.closeDrawers();
                } else {
                    // open
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });

        //TextView txtlogo = (TextView) findViewById(R.id.H_txttollbar);
        //txtlogo.setTypeface(typefaceHeader);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main_home);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            email = extras.getString("email");
            firstName = extras.getString("name");
            lastName = extras.getString("lastName");
            imageUrl = extras.get("imageUrl").toString();

            if (imageUrl != null) {
                new DownloadImage((CircleImageView) findViewById(R.id.H_profile_image), HomeActivity.this).execute(imageUrl);
            }
        }

        // CircleImageView _profileView = (CircleImageView) findViewById(R.id.H_profile_image);
        LinearLayout _translate = (LinearLayout) findViewById(R.id.btnTranslation);
        LinearLayout _elQuibla = (LinearLayout) findViewById(R.id.btnElquibla);
        LinearLayout _location = (LinearLayout) findViewById(R.id.btnActivityFinder);
        LinearLayout _uniteConvert = (LinearLayout) findViewById(R.id.btnConversion);
        //  CircleImageView _signout = (CircleImageView) findViewById(R.id.H_Signout_image);
        // CircleImageView _exit = (CircleImageView) findViewById(R.id.H_Exite_image);

        /*_profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, ProfileActivity.class);
                trabslateCa.putExtra("email", email);
                trabslateCa.putExtra("firstName", firstName);
                trabslateCa.putExtra("lastName", lastName);
                trabslateCa.putExtra("imageUrl", imageUrl);
                startActivity(trabslateCa);
            }
        });*/

        _translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, TranslationPage.class);
                startActivity(trabslateCa);
            }
        });

        /*_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(HomeActivity.this, LoginFacebook.class);
                startActivity(login);
                finish();
            }
        });*/

        _elQuibla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elquiblaCa = new Intent(HomeActivity.this, QuiblaCollection.class);
                startActivity(elquiblaCa);
            }
        });

   /*     _exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clearApplicationData();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);/*//***Change Here***
         startActivity(intent);
         HomeActivity.this.finishAffinity();
         System.exit(0);

         } catch (Exception e) {
         Intent callList = new Intent(HomeActivity.this, LoginActivity.class);
         startActivity(callList);
         }
         }
         });
         */
        _location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, City_GuideActivity.class);
                startActivity(trabslateCa);
            }
        });

        _uniteConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationCa = new Intent(HomeActivity.this, UniteConverterActivity.class);
                startActivity(locationCa);
            }
        });

        //imgConvert = (ImageView) findViewById(R.id.imgConvert);
        //imgcurrentloaction = (ImageView) findViewById(R.id.imgCurrentLocation);
        //imgElquibla = (ImageView) findViewById(R.id.imgElquibla);
        //imgTranslation = (ImageView) findViewById(R.id.imgTranslation);

/*        imgConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, UniteConverterActivity.class);
                startActivity(trabslateCa);
            }
        });

        imgcurrentloaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, City_GuideActivity.class);
                startActivity(trabslateCa);
            }
        });
        imgElquibla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, Muazzin.class);
                startActivity(trabslateCa);
            }
        });
        imgTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trabslateCa = new Intent(HomeActivity.this, TranslateActivity.class);
                startActivity(trabslateCa);
            }
        });*/

      /* Bundle inBundle = getIntent().getExtras();

        email    = inBundle.get("email").toString();
        birthday = inBundle.get("birthday").toString();
        firstName = inBundle.get("firstName").toString();
        lastName = inBundle.get("lastName").toString();
        gender = inBundle.get("gender").toString();
        location = inBundle.get("location").toString();
        idFacebook = inBundle.get("idFacebook").toString();
        acssesstokenFce = inBundle.get("acssesstokenFce").toString();
        imageUrl = inBundle.get("imageUrl").toString();

        TextView txtUserName=(TextView)findViewById(R.id.H_txtUserName);
        txtUserName.setText(firstName);
        txtUserName.setTypeface(typeface);

        new DownloadImage((ImageView) findViewById(R.id.H_profile_image),HomeActivity.this).execute(imageUrl);*/

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
            // Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }


    }

    public void clearApplicationData() {

        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {

            String[] fileNames = applicationDirectory.list();

            for (String fileName : fileNames) {

                if (!fileName.equals("lib")) {

                    deleteFile(new File(applicationDirectory, fileName));

                }

            }

        }

    }

    public static boolean deleteFile(File file) {

        boolean deletedAll = true;

        if (file != null) {

            if (file.isDirectory()) {

                String[] children = file.list();

                for (int i = 0; i < children.length; i++) {

                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;

                }

            } else {

                deletedAll = file.delete();

            }

        }

        return deletedAll;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Do you want to go out ?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            finish();
                            System.exit(0);
                        }
                    }).create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        // return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("longitudeNetwork", String.valueOf(longitudeNetwork));
                    editor.putString("latitudeNetwork", String.valueOf(latitudeNetwork));
                    editor.commit();
                    //longitudeValueNetwork.setText(longitudeNetwork + "");
                    // latitudeValueNetwork.setText(latitudeNetwork + "");
                    // Toast.makeText(HomeActivity.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
}
