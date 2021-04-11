package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;

public class DateFragment extends Fragment {

    DatePicker picker;
    TextView next;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        next = (TextView) view.findViewById(R.id.bt_submit);

        picker=(DatePicker) view.findViewById(R.id.datePicker1);


//        String day = "Day = " + picker.getDayOfMonth();
//        String month = "Month = " + (picker.getMonth() + 1);
//        String year = "Year = " + picker.getYear();
//
//        date =  day + "\n" + month + "\n" + year;


        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String currentDate = (monthOfYear+1) + "/" + dayOfMonth + "/" + year;
//                date = dayOfMonth +"/"+ (monthOfYear+1) +"/" +year;

                date = year +"/" +(monthOfYear+1) +"/"+dayOfMonth;

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("date",date);

                editor.putString("i_birth",date);
                editor.apply();
                editor.commit();

                ((PatientDashboardActivity)getActivity()).replaceFragment(new pquestiononeFragment());

            }
        });


        return view;
    }
}