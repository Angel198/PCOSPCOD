package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;

public class PquestionthreeFragment extends Fragment {

    TextView next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String value;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pquestionthree, container, false);

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        next = (TextView) view.findViewById(R.id.bt_submit);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedID);

                value = radioButton.getText().toString();

                Log.i("value",value);
                editor.putString("i_three_value",value);
                editor.apply();
                editor.commit();

                ((PatientDashboardActivity)getActivity()).replaceFragment(new PquestionfourFragment());

            }
        });


        return view;
    }
}