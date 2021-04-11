package com.jaylax.pcospcod.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientadapter.NotificationAdapter;

public class NotificationFragment extends Fragment {

    GridView gridView;
    NotificationAdapter notificationAdapter;
    String[] name = {"Chakrila joyanto","Waiano Akarana","Laura Sofia Urena", "Hugo Assuncao","Salom name"};

    String[] detail = {"Appointment Booked","Appointment Booked","Request Accepted","Appointment Booked","Request Accepted"};

    String[] status = {"2 min ago", "20 min ago","11 hr ago","08:10 Pm 12 January","12:10 Pm January"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Notification");

        gridView = (GridView) view.findViewById(R.id.gridview);

        notificationAdapter = new NotificationAdapter(getContext(),name,detail,status);

        gridView.setAdapter(notificationAdapter);

        return view;
    }
}