package com.jaylax.pcospcod;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;

import com.jaylax.pcospcod.doctoractivities.DoctorDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;

public class MainActivity extends AppCompatActivity {

    private static int progress;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private PowerManager.WakeLock wl;
    @SuppressLint("InvalidWakeLockTag")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
        progress = 0;

        new Thread(new Runnable() {
            public void run() {
                // ---do some work here---
                while (progressStatus < 100) {
                    progressStatus = doSomeWork();
                    // ---Update the progress bar---
                    handler.post(new Runnable() {
                        public void run() {

                        }
                    });
                }
                // ---hides the progress bar---
                handler.post(new Runnable() {
                    // @SuppressLint("ParserError")
                    public void run() {
                        finish();

                        SharedPreferences setting  = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO,Context.MODE_PRIVATE);
                        boolean hasLoggedIn = setting.getBoolean("hasLoggedIn",false);

                        SharedPreferences cus = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
                        boolean loggedIn = cus.getBoolean("hasLogged",false);

                        Log.i("log", String.valueOf(loggedIn));

                        if (loggedIn)
                        {
                            cus.getString("userid",null);
                            cus.getString("token",null);
                            startActivity(new Intent(MainActivity.this, PatientDashboardActivity.class));

                        }
                        else if(hasLoggedIn)
                        {
                            setting.getString("userid",null);
                            setting.getString("token",null);
                            Log.i("id",setting.getString("userid",null));

                            startActivity(new Intent(MainActivity.this, DoctorDashboardActivity.class));

                        }
                        else
                        {

                            Intent intent = new Intent(MainActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }

                        overridePendingTransition(R.anim.push_up_out,R.anim.push_up_in);
                    }
                });
            }
            private int doSomeWork() {
                try {
                    // ---simulate doing some work---
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ++progress;
            }
        }).start();

    }

}