package com.jaylax.pcospcod.patienteditprofile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.fragment.AgeFragment;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientProfileActivity;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONObject;

import java.util.HashMap;

public class EditPatientHeightFragment extends Fragment {

    TextView next;
    NumberPicker n1, n2;
    String _n1, _n2 , height;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_patient_height, container, false);

        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);

        next = (TextView) view.findViewById(R.id.done);
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

                class UserLogin extends AsyncTask<Void, Void, String> {

                    ProgressBar progressBar;

                    @Override
                    protected void onPreExecute() {
                        Log.d("newwwss", "Login Function Called PreExecute");
                        super.onPreExecute();
                        progressBar = new ProgressBar(getContext());
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(String s) {

                        super.onPostExecute(s);
                        progressBar.setVisibility(View.GONE);
                        Log.d("response", s);

                        try {

                            Log.d("response", s);
                            JSONObject obj = new JSONObject(s);

                            String rs = obj.getString("success");

                            if (rs.equals("true"))
                            {
                                getFragmentManager().popBackStack();
                                ((PatientProfileActivity)getActivity()).setvisibility();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @SuppressLint("WrongThread")
                    @Override
                    protected String doInBackground(Void... voids) {
                        //Creating request handler object
                        RequestHandler requestHandler = new RequestHandler();

                        //Creating request parameters
                        HashMap<String, String> params = new HashMap<>();

                        params.put("userid", user_id);
                        params.put("height",height);

                        return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/update_patient_profile", params);

                    }

                }

                UserLogin ul = new UserLogin();
                ul.execute();

            }
        });



        return view;

    }

}