package com.uniquestartup.mohamedaliheeba.sawa7.TimeLine;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniquestartup.mohamedaliheeba.sawa7.R;

import java.util.List;

public class EventsAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<TimelineObject> allEvents = null;
    private ImageLoader imageLoader;

    public EventsAdapter(Context context, List<TimelineObject> allEvents) {

        Context mContext = context;
        this.allEvents = allEvents;
        inflater = LayoutInflater.from(mContext);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {

        ImageView eventsPhoto;
        TextView eventsDate;
        TextView eventsName;
        TextView eventsDJ1;
        TextView eventsDJ2;
        TextView eventsLocation;

    }

    @Override
    public int getCount() {
        return allEvents.size();
    }

    @Override
    public TimelineObject getItem(int position) {
        return allEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {

            view = inflater.inflate(R.layout.one_row_events, parent, false);

            holder = new ViewHolder();

            holder.eventsPhoto = (ImageView) view.findViewById(R.id.ivEventsPhoto);
            holder.eventsDate = (TextView) view.findViewById(R.id.tvEventsDate);
            holder.eventsName = (TextView) view.findViewById(R.id.tvEventsName);
            holder.eventsDJ1 = (TextView) view.findViewById(R.id.tvEventsDJ1);
            holder.eventsDJ2 = (TextView) view.findViewById(R.id.tvEventsDJ2);
            holder.eventsLocation = (TextView) view.findViewById(R.id.tvEventsLocation);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        imageLoader.DisplayImage(allEvents.get(position).getTimeline_image_url(), holder.eventsPhoto);

        holder.eventsDate.setText(allEvents.get(position).getCity());

        holder.eventsName.setText(allEvents.get(position).getCountry());

        holder.eventsDJ1.setText(allEvents.get(position).getDescription_area());

        holder.eventsDJ2.setText(allEvents.get(position).getUser_email());

        holder.eventsLocation.setText(allEvents.get(position).getUser_name());


        return view;
    }
}