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


public class PquestioneightFragment extends Fragment {

    TextView next;
    RadioGroup radioGroup, radioGroup1, radioGroup2, radioGroup3, radioGroup4;
    RadioButton radioButton, radioButton1, radioButton2, radioButton3, radioButton4;
    String value, value1, value2, value3, value4;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pquestioneight, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        next = (TextView) view.findViewById(R.id.bt_submit);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        radioGroup1 = (RadioGroup) view.findViewById(R.id.radio_group1);
        radioGroup2 = (RadioGroup) view.findViewById(R.id.radio_group2);
        radioGroup3 = (RadioGroup) view.findViewById(R.id.radio_group3);
        radioGroup4 = (RadioGroup) view.findViewById(R.id.radio_group4);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedID);
                value = radioButton.getText().toString();

                int selectedID1 = radioGroup1.getCheckedRadioButtonId();
                radioButton1 = (RadioButton) view.findViewById(selectedID1);
                value1 = radioButton1.getText().toString();

                int selectedID2 = radioGroup2.getCheckedRadioButtonId();
                radioButton2 = (RadioButton) view.findViewById(selectedID2);
                value2 = radioButton2.getText().toString();

                int selectedID3 = radioGroup3.getCheckedRadioButtonId();
                radioButton3 = (RadioButton) view.findViewById(selectedID3);
                value3 = radioButton3.getText().toString();

                int selectedID4 = radioGroup4.getCheckedRadioButtonId();
                radioButton4 = (RadioButton) view.findViewById(selectedID4);
                value4 = radioButton4.getText().toString();

                Log.i("aa",value);
                Log.i("a",value1);
                Log.i("av",value2);
                Log.i("as",value3);
                Log.i("ad",value4);

                editor.putString("i_eight_value",value);
                editor.putString("i_nine_value",value1);
                editor.putString("i_ten_value",value2);
                editor.putString("i_eleven_value",value3);
                editor.putString("i_twelve_value",value4);
                editor.apply();
                editor.commit();


                ((PatientDashboardActivity)getActivity()).replaceFragment(new PquestionnineFragment());


            }
        });


        return view;
    }
}