package com.jaylax.pcospcod.doctoractivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorChatActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat);

        sharedPreferences = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        token = sharedPreferences.getString("device_token_fcm",null);

        patient_id = getIntent().getStringExtra("patient_ID");


    }

}