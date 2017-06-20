package com.uniquestartup.mohamedaliheeba.sawa7.TimeLine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import java.util.ArrayList;
import java.util.List;
import com.uniquestartup.mohamedaliheeba.sawa7.BackendLess.BackendSettings;
import com.uniquestartup.mohamedaliheeba.sawa7.R;

public class TimelineActivity extends AppCompatActivity {
    private List<TimelineObject> allEvents = new ArrayList<>();
    private ListView listview;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Backendless.initApp(this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        listview = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
                    EventsAdapter adapter = new EventsAdapter(TimelineActivity.this, allEvents);
                    listview.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                String _message=backendlessFault.getMessage();
            }
        });

    }
}