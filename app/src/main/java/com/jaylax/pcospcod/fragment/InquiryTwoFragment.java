package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;


public class InquiryTwoFragment extends Fragment {

    TextView next;
    NumberPicker n1, n2;
    String _n1, _n2 , height;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inquiry_two, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        next = (TextView) view.findViewById(R.id.bt_submit);
        n1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        n2 = (NumberPicker) view.findViewById(R.id.numberPicker2);

        n1.setMinValue(0);
        n1.setMaxValue(10);
        n1.setWrapSelectorWheel(true);

        n2.setMinValue(0);
        n2.setMaxValue(20);
        n2.setWrapSelectorWheel(true);


        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                _n1 = String.valueOf(newVal);
            }
        });

        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                _n2 = String.valueOf(newVal);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                height = _n1 +" Feet " + _n2 +" Inch ";

                Log.i("height",height);

                editor.putString("i_height",height);
                editor.apply();
                editor.commit();

                ((PatientDashboardActivity)getActivity()).replaceFragment(new AgeFragment());

            }
        });


        return view;

    }
}