package com.jaylax.pcospcod;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

    EditText number,code,name;
    FloatingActionButton login;
    String _number,_code,_name;
    ImageView doctor;
    private DatabaseReference databaseReference;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;
    private TextView currentAddTv;
    private Location currentLocation;
    private LocationCallback locationCallback;
    String[] aa = new String[]{};
    String p, lo, androidId, m_Id;
    public static final String MyPREFERENCES = "ActivityPREF";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    String newToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number = (EditText) findViewById(R.id.number);
        name = (EditText) findViewById(R.id.name);
        login = (FloatingActionButton) findViewById(R.id.login);
        doctor = (ImageView) findViewById(R.id.doctor_view);

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            };
        };
        startLocationUpdates();

        sp = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        editor = sp.edit();

        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.i("id",androidId);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _number = number.getText().toString();
                _name = name.getText().toString();

                String phone = "+91" + _number;

                class UserLogin extends AsyncTask<Void, Void, String> {

                    ProgressBar progressBar;

                    @Override
                    protected void onPreExecute() {
                        Log.d("newwwss", "Login Function Called PreExecute");
                        super.onPreExecute();
                        progressBar = new ProgressBar(LoginActivity.this);
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
                            JSONObject jj = obj.getJSONObject("data");

                            String user = jj.getString("token");
                            String nn = jj.getString("name");
                            String user_id = jj.getString("userid");
                            String ss= jj.getString("device_token");

                            Log.i("r_name",nn);

                            databaseReference = FirebaseDatabase.getInstance().getReference().child(newToken);

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                {

                                    databaseReference.child("user_id").setValue(user_id);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            Intent intent = new Intent(LoginActivity.this,OTPActivity.class);
                            editor.putString("userid", user_id);
                            editor.putString("token",user);
                            editor.putString("nn", nn);
                            editor.putString("device_token_fcm",ss);
                            editor.putBoolean("hasLogged", true);
                            editor.apply();
                            editor.commit();
                            intent.putExtra("number",phone);
                            intent.putExtra("human","1");
                            intent.putExtra("name",_name);
                            startActivity(intent);


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

                        params.put("name", _name);
                        params.put("mobile_number", _number);
                        params.put("city", lo);
                        params.put("user_type", "1");
                        params.put("device_token",newToken);
                        return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/login", params);

                    }

                }

                UserLogin ul = new UserLogin();
                ul.execute();

            }

        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,DoctorLoginActivity.class);
                startActivity(intent);

            }
        });

    }


    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(LoginActivity.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }



    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(LoginActivity.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");

            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){

        aa = currentAdd.split(",");
        lo = aa[0];
        p = aa[1];

        Log.i("lo",lo);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


}