package com.jaylax.pcospcod.doctoractivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;

public class DoctorVideoCallActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_video_call);

        sharedPreferences = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);


    }
}