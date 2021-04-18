package com.jaylax.pcospcod.patientadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaylax.pcospcod.R;

public class NotificationAdapter extends BaseAdapter {

    Context context;
    int[] logos;
    String[] name;
    String[] detail;
    String[] status;
    LayoutInflater inflter;

    public NotificationAdapter(Context applicationContext,  String[] name, String[] detail, String[] status) {
        this.context = applicationContext;
//        this.logos = logos;
        this.name = name;
        this.detail = detail;
        this.status = status;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return detail.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.notification_layout, null); // inflate the layout

//        ImageView icon = (ImageView) view.findViewById(R.id.profile_image); // get the reference of ImageView
        TextView _name = (TextView) view.findViewById(R.id.name);
        TextView _detail = (TextView) view.findViewById(R.id.book);
//        TextView _status = (TextView) view.findViewById(R.id.time_);


//        icon.setImageResource(logos[i]); // set logo images
        _name.setText(name[i]);
        _detail.setText(detail[i]);
//        _status.setText(status[i]);


        return view;

    }
}
