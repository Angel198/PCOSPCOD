package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;

public class StatusFragment extends Fragment {

    TextView next;
    TextView single, married;
    String value;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_status, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");


        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        next = (TextView) view.findViewById(R.id.bt_submit);
        single = (TextView) view.findViewById(R.id.single);
        married = (TextView) view.findViewById(R.id.married);

        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value = "single";
                single.setBackgroundResource(R.color.green_500);

            }
        });

        married.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = "married";
                married.setBackgroundResource(R.color.green_500);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("i_status",value);
                editor.apply();
                editor.commit();

                ((PatientDashboardActivity)getActivity()).replaceFragment(new DateFragment());

            }
        });


        return view;
    }
}