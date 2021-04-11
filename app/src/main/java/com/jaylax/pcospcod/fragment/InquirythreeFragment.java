package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;
import com.shawnlin.numberpicker.NumberPicker;

public class InquirythreeFragment extends Fragment {

    TextView next;
    NumberPicker n1;
    String weight;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inquirythree, container, false);

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        next = (TextView) view.findViewById(R.id.bt_submit);

        n1 = (NumberPicker) view.findViewById(R.id.numberPicker1);

        n1.setMinValue(0);
        n1.setMaxValue(100);

        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                weight = String.valueOf(newVal);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("weight",weight);
                editor.putString("i_weight",weight);
                editor.apply();
                editor.commit();

                ((PatientDashboardActivity)getActivity()).replaceFragment(new InquiryTwoFragment());

            }
        });
        return view;

    }

}