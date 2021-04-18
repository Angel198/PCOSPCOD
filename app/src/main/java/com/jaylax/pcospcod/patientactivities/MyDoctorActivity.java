package com.jaylax.pcospcod.patientactivities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.VideoCalling.BaseActivity;
import com.jaylax.pcospcod.VideoCalling.CallScreenActivity;
import com.jaylax.pcospcod.VideoCalling.SinchService;
import com.jaylax.pcospcod.util.RequestHandler;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.appcompat.widget.Toolbar;


public class MyDoctorActivity extends BaseActivity implements SinchService.StartFailedListener {

    CircularImageView circularImageView;
    TextView name, specialization, about, contact, mail;
    FloatingActionButton voice_call, video_call;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, _name, token;
    String doc_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctor);

        initToolbar();

        circularImageView = (CircularImageView) findViewById(R.id.doctor_image);
        name = (TextView) findViewById(R.id.doctor_name);
        specialization = (TextView) findViewById(R.id.doctor_specialization);
        about = (TextView) findViewById(R.id.doctor_about);
        contact = (TextView) findViewById(R.id.doctor_contact);
        mail = (TextView) findViewById(R.id.doctor_email);
//        voice_call = (FloatingActionButton) findViewById(R.id.voice_call);
//        video_call = (FloatingActionButton) findViewById(R.id.call_video);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        _name = sharedPreferences.getString("nn",null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
        }


        getdata();



//        video_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MyDoctorActivity.this, DoctorProfileActivity.class);
//                startActivity(intent);
//
//            }
//
//        });


    }
    protected void onServiceConnected() {
//        mLoginButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);
    }

    private void loginClicked() {
        String userName = user_id;

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);

        }
    }
    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient, menu);

//        MenuItem myActionMenuItem = menu.findItem(R.id.action_share);

        MenuItem menu_item = menu.findItem(R.id.action_video);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home )
        {
            finish();
        }
        else if (id == R.id.action_video)
        {
//            Intent intent = new Intent(MyDoctorActivity.this, VideoCallActivity.class);
//            startActivity(intent);
            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(user_id);
//                showSpinner();
            } else {
//                openPlaceCallActivity();
                callButtonClicked();
            }

        }

        return super.onOptionsItemSelected(item);

    }
    private void callButtonClicked() {
        String userName = doc_id;
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = getSinchServiceInterface().callUserVideo(userName);
        String callId = call.getCallId();

        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra( SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }

    public void getdata()
    {

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                Log.d("newwwss", "Login Function Called PreExecute");
                super.onPreExecute();
                progressBar = new ProgressBar(getApplicationContext());
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
                        doc_id = c.getString("id");
                        String d = c.getString("specialization");
                        String e = c.getString("hospital_address");
                        String f = c.getString("email");
                        String g = c.getString("mobile_number");
                        String h = c.getString("description");
                        String j = c.getString("profile_image_url");


//                        editor.putString("doctor_id",doc_id);
//                        editor.apply();
//                        editor.commit();

                        byte[] decodestring = Base64.decode(j,Base64.DEFAULT);
                        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
                        loginClicked();

                        name.setText(a);
                        specialization.setText(d);
                        about.setText(h);
                        mail.setText(f);
                        contact.setText(g);
                        circularImageView.setImageBitmap(decodeByte);
                        loginClicked();
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

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStarted() {

    }
}