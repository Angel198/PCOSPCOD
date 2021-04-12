package com.jaylax.pcospcod.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.fragment.app.Fragment;

public class ChatFragment extends Fragment {

    EditText send_msg;
    ImageButton send;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token, doctor_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Chat");

        send_msg = (EditText) view.findViewById(R.id.edittxt_chat_Message);
        send = (ImageButton) view.findViewById(R.id.btnSend);


        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        token = sharedPreferences.getString("device_token_fcm",null);
        editor = sharedPreferences.edit();

        Log.i("user_id",user_id);


        return view;


    }

    public void getdata()
    {

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

                try {

                    Log.d("response", s);
                    JSONObject obj = new JSONObject(s);
                    JSONArray jsonArray = obj.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); ++i) {

                        JSONObject c = jsonArray.getJSONObject(i);

                        String a = c.getString("name");
                        doctor_id = c.getString("id");
                        String d = c.getString("specialization");
                        String e = c.getString("hospital_address");
                        String f = c.getString("email");
                        String g = c.getString("mobile_number");
                        String h = c.getString("description");
                        String j = c.getString("profile_image_url");


                    }


                } catch (JSONException e) {
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

                params.put("patient_id", user_id);

                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/get_patient_doctor", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }

}