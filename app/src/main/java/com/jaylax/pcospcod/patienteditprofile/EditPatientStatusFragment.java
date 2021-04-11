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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.fragment.DateFragment;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientProfileActivity;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONObject;

import java.util.HashMap;


public class EditPatientStatusFragment extends Fragment {

    TextView next;
    TextView single, married;
    String value;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_patient_status, container, false);

        next = (TextView) view.findViewById(R.id.done);
        single = (TextView) view.findViewById(R.id.single);
        married = (TextView) view.findViewById(R.id.married);

        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);


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

                Log.i("status",value);

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
                        params.put("status",value);

                        return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/update_patient_profile", params);

                    }

                }

                UserLogin ul = new UserLogin();
                ul.execute();

//                getFragmentManager().popBackStack();
//                ((PatientProfileActivity)getActivity()).setvisibility();

            }
        });


        return view;

    }

}