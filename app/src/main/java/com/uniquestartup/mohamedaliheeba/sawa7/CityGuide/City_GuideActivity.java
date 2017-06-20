package com.uniquestartup.mohamedaliheeba.sawa7.CityGuide;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.TimeLine.EventsAdapter;
import com.uniquestartup.mohamedaliheeba.sawa7.TimeLine.TimelineActivity;
import com.uniquestartup.mohamedaliheeba.sawa7.TimeLine.TimelineObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

public class City_GuideActivity extends AppCompatActivity {

    private List<TimelineObject> allEvents = new ArrayList<>();
    private ListView listview;
    private ProgressBar progressBar;


    ArrayList<FoursquareVenue> venuesList;

    // the foursquare client_id and the client_secret

    // ============== YOU SHOULD MAKE NEW KEYS ====================//
    final String CLIENT_ID = "VBBHOXYWE2LSKY1OKXRWPNV1VSFWXZJTSA1ZRTVCV0GN0TBZ";
    final String CLIENT_SECRET = "WVDU2GMNF354EL0J3HTA2JSC3LP1XLAM40BI0YEZQ1NLXGNE";

    // we will need to take the latitude and the logntitude from a certain point
    // this is the center of New York
    String latitude = "29.9592499";
    String longtitude = "31.2598281";
    String Query = "Coffee";

    ArrayAdapter<String> myAdapter;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city__guide);
        Resources res = getResources();
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", res.getDrawable(this.getResources().getIdentifier("searchicon", "drawable", this.getPackageName())));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", res.getDrawable(this.getResources().getIdentifier("meicon", "drawable", this.getPackageName())));
        host.addTab(spec);
        // start the AsyncTask that makes the call for the venus search.

        ((EditText) findViewById(R.id.etextQueryKey)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (!event.isShiftPressed()) {
                                // the user is done typing.
                                EditText eQuery = (EditText) findViewById(R.id.etextQueryKey);
                                Query = eQuery.getText().toString();
                                new fourquare().execute();
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                });

        new fourquare().execute();

        /////////////////////////////////////
        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        listview = (ListView) findViewById(R.id.listTimeLine);
        progressBar = (ProgressBar) findViewById(R.id.progressBarTimeLine);
        BackendlessDataQuery query = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        String whereClause = "created";
        queryOptions.addSortByOption(whereClause);
        query.setQueryOptions(queryOptions);

        Backendless.Data.of(TimelineObject.class).find(query, new AsyncCallback<BackendlessCollection<TimelineObject>>() {

            @Override
            public void handleResponse(BackendlessCollection<TimelineObject> eventsListBackendlessCollection) {

                progressBar.setVisibility(View.VISIBLE);
                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500);
                animation.setDuration(5000);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();

                TimelineObject eventsList;
                for (TimelineObject totalEvents : eventsListBackendlessCollection.getCurrentPage()) {

                    eventsList = new TimelineObject();

                    eventsList.setTimeline_image_url(totalEvents.getTimeline_image_url());
                    eventsList.setCountry(totalEvents.getCountry());
                    eventsList.setCity(totalEvents.getCity());
                    eventsList.setDescription_area(totalEvents.getDescription_area());
                    eventsList.setUser_email(totalEvents.getUser_email());
                    eventsList.setUser_name(totalEvents.getUser_name());

                    allEvents.add(eventsList);
                    EventsAdapter adapter = new EventsAdapter(City_GuideActivity.this, allEvents);
                    listview.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                String _message = backendlessFault.getMessage();
            }
        });
        //////////////////////////
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            longtitude = prefs.getString("longitudeNetwork", "30.0444");//"No name defined" is the default value.
            latitude = prefs.getString("latitudeNetwork", "31.2357"); //0 is the default value.
        }
    }

    private class fourquare extends AsyncTask<View, Void, String> {

        String temp;

        @Override
        protected String doInBackground(View... urls) {
            // make Call to the url

            temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll=" + latitude + "," + longtitude + "&query=" + Query + "");
            return "";
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(String result) {
            if (temp == null) {
                // we have an error to the call
                // we can also stop the progress bar
            } else {
                // all things went right

                // parseFoursquare venues search result
                venuesList = (ArrayList<FoursquareVenue>) parseFoursquare(temp);

                List<String> listTitle = new ArrayList<String>();

                for (int i = 0; i < venuesList.size(); i++) {
                    // make a list of the venus that are loaded in the list.
                    // show the name, the category and the city
                    listTitle.add(i, venuesList.get(i).getName() + ", " + venuesList.get(i).getCategory() + "" + venuesList.get(i).getCity() + "," + venuesList.get(i).getContact_phone() + "," + venuesList.get(i).getContact_facebookUsername() + "," + venuesList.get(i).getContact_twitter() + "," + venuesList.get(i).getSiteUrl());
                }

                // set the results to the list
                // and show them in the xml
                myAdapter = new ArrayAdapter<String>(City_GuideActivity.this, R.layout.row_layout, R.id.listText, listTitle);
                ListView listView = (ListView) findViewById(R.id.listVenue);
                //listView.setAdapter(myAdapter);
                listView.setAdapter(new CustomAdapter(City_GuideActivity.this, venuesList));
            }
        }
    }

    public static String makeCall(String url) {

        // string buffers the url
        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";

        // instanciate an HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        // instanciate an HttpGet
        HttpGet httpget = new HttpGet(buffer_string.toString());

        try {
            // get the responce of the httpclient execution of the url
            HttpResponse response = httpclient.execute(httpget);
            InputStream is = response.getEntity().getContent();

            // buffer input stream the result
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            // the result as a string is ready for parsing
            replyString = new String(baf.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // trim the whitespaces
        return replyString.trim();
    }

    private static ArrayList<FoursquareVenue> parseFoursquare(final String response) {

        ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareVenue poi = new FoursquareVenue();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).getString("name"));

                            if (jsonArray.getJSONObject(i).has("url")) {
                                poi.setSiteUrl(jsonArray.getJSONObject(i).getString("url"));
                            }
                            if (jsonArray.getJSONObject(i).has("location")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")) {
                                        poi.setCity(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("distance")) {
                                            poi.setDistant(jsonArray.getJSONObject(i).getJSONObject("location").getString("distance") + " " + jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                        }

                                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("formattedAddress")) {
                                            String Fullpath = "";
                                            if (jsonArray.getJSONObject(i).getJSONObject("location").getJSONArray("formattedAddress").length() > 0) {
                                                for (int j = 0; j < jsonArray.getJSONObject(i).getJSONObject("location").getJSONArray("formattedAddress").length(); j++) {
                                                    Fullpath += " " + jsonArray.getJSONObject(i).getJSONObject("location").getJSONArray("formattedAddress").getString(j);
                                                }
                                                poi.setFormattedAddress(Fullpath);
                                            }
                                            // poi.setFormattedAddress(jsonArray.getJSONObject(i).getJSONObject("location").getString("distance")+" "+jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                        }
                                    }

                                    if (jsonArray.getJSONObject(i).has("categories")) {
                                        if (jsonArray.getJSONObject(i).getJSONArray("categories").length() > 0) {
                                            if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
                                                poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                                                poi.setIconUrl(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getJSONObject("icon").getString("prefix") + jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getJSONObject("icon").getString("suffix"));
                                            }
                                        }
                                    }
                                    if (jsonArray.getJSONObject(i).has("contact")) {
                                        if (jsonArray.getJSONObject(i).getJSONObject("contact").has("formattedPhone")) {
                                            poi.setContact_phone(jsonArray.getJSONObject(i).getJSONObject("contact").getString("formattedPhone"));
                                        }
                                        if (jsonArray.getJSONObject(i).getJSONObject("contact").has("twitter")) {
                                            poi.setContact_twitter(jsonArray.getJSONObject(i).getJSONObject("contact").getString("twitter"));
                                        }
                                        if (jsonArray.getJSONObject(i).getJSONObject("contact").has("facebookUsername")) {
                                            poi.setContact_facebookUsername(jsonArray.getJSONObject(i).getJSONObject("contact").getString("facebookUsername"));
                                        }
                                    }
                                    temp.add(poi);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FoursquareVenue>();
        }
        return temp;

    }
}