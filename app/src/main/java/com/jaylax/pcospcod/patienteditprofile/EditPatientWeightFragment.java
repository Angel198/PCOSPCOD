package com.jaylax.pcospcod.patienteditprofile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.RequestHandler;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.fragment.app.Fragment;

public class EditPatientWeightFragment extends Fragment {

    TextView next;
    NumberPicker n1;
    String weight;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_patient_weight, container, false);

        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);

//        if (getArguments() != null) {
//
//            String strtext = this.getArguments().getString("weight");
//            n1.setValue(Integer.parseInt(strtext));
//
//        }

        next = (TextView) view.findViewById(R.id.done);

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

                Log.i("weight", weight);
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

                                new Handler().post(new Runnable() {

                                    @Override
                                    public void run()
                                    {
                                        Intent intent = getActivity().getIntent();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        getActivity().overridePendingTransition(0, 0);
                                        getActivity().finish();

                                        startActivity(intent);
                                    }
                                });
//                                getFragmentManager().popBackStack();
//                                ((PatientProfileActivity)getActivity()).setvisibility();

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
                        params.put("weight",weight);

                        return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/update_patient_profile", params);

                    }

                }

                UserLogin ul = new UserLogin();
                ul.execute();

//                getFragmentManager().popBackStack();
//                ((PatientProfileActivity)getActivity()).setvisibility();
//                getActivity().onBackPressed();

            }
        });

        return view;

    }


}