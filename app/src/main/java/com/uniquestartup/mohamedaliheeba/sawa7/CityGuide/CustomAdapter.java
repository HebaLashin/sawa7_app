package com.uniquestartup.mohamedaliheeba.sawa7.CityGuide;

/**
 * Created by Moham on 23/09/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uniquestartup.mohamedaliheeba.sawa7.GenaricClass.DownloadImage;
import com.uniquestartup.mohamedaliheeba.sawa7.R;
import com.uniquestartup.mohamedaliheeba.sawa7.activity.ProfileActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends BaseAdapter{
    ArrayList<FoursquareVenue> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(City_GuideActivity mainActivity, ArrayList<FoursquareVenue> prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.txtPlaceName);
        holder.tv2=(TextView) rowView.findViewById(R.id.txtcatogryName);
        holder.tv3=(TextView) rowView.findViewById(R.id.txtDistatBetween);
        holder.tv4=(TextView) rowView.findViewById(R.id.txtFullAddress);
        holder.img=(ImageView) rowView.findViewById(R.id.imgIcon);
        holder.tv1.setText(result.get(position).getName());
        holder.tv2.setText(result.get(position).getCategory());
        holder.tv3.setText(result.get(position).getDistant());
        holder.tv4.setText(result.get(position).getFormattedAddress());
        //new DownloadImage(holder.img, context).execute("https://irs3.4sqi.net/img/general/27272694_5-aHNd_1X_gWh_4dadAkaFUp2NGjTjn6eZ2BYtU_1aE.jpg");
       // new DownloadImage(holder.img, context).execute(result.get(position).getIconUrl());
       // holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}
