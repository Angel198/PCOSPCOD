package com.jaylax.pcospcod.patienteditprofile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.fragment.StatusFragment;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientProfileActivity;
import com.shawnlin.numberpicker.NumberPicker;


public class EditPatientAgeFragment extends Fragment {

    TextView next;
    NumberPicker n1;
    String age;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_patient_age, container, false);

        next = (TextView) view.findViewById(R.id.done);
        n1 = (NumberPicker) view.findViewById(R.id.numberPicker1);

        n1.setMinValue(0);
        n1.setMaxValue(100);

        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                age = String.valueOf(newVal);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("age",age);

                getFragmentManager().popBackStack();
                ((PatientProfileActivity)getActivity()).setvisibility();
//                editor.putString("i_age",age);
//                editor.apply();
//                editor.commit();
//
//                ((PatientDashboardActivity)getActivity()).replaceFragment(new StatusFragment());

            }
        });


        return  view;
    }
}