package com.jaylax.pcospcod.patientactivities;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaylax.pcospcod.Constants;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.MyNotificationManager;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.CalendarModelTreatment;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OngoingTreatmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CalendarView calender;
    String date = null;
    Calendar c = Calendar.getInstance();
    RecyclerAdapter recyclerAdapter;
    TextView t_date, t_Day, t_number;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    ArrayList<CalendarModelTreatment> calendarModelTreatments = new ArrayList<>();
    String treatment_date, treatment_status, current_Date, T_date, S_date;
    Calendar calendar;
    String i_ring = "null";

    Boolean isFromNotification = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_treatment);

        initToolbar();

        if (getIntent().hasExtra("fromNotification")) {
            isFromNotification = getIntent().getBooleanExtra("fromNotification", false);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        calender = (CalendarView) findViewById(R.id.calendar_view);
        t_date = (TextView) findViewById(R.id.date);
        t_Day = (TextView) findViewById(R.id.treatment_day);
        t_number = (TextView) findViewById(R.id.treatment_number);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid", null);
        name = sharedPreferences.getString("nn", null);
        editor = sharedPreferences.edit();

        Log.i("user_id", user_id);

        calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        current_Date = mdformat.format(calendar.getTime());

        Log.i("current_Date", current_Date);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        S_date = sharedPreferences.getString("s_date", null);

        if (isFromNotification) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.treatment_alert_msg);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO : Call API on Yes click


                    getdata_completed(S_date);
                }
            });

            builder.setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getdata_skipped(S_date);

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }

        if (current_Date.equals(S_date) && !isFromNotification) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
                mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                mNotificationManager.createNotificationChannel(mChannel);

            }

            MyNotificationManager.getInstance(getApplicationContext()).displayNotification("PCOS PCOD", "You have your treatment today");

        } else {
            Log.i("dd", "No");
        }

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                calendarModelTreatments.clear();

                String Date1 = year + "-" + (month + 1) + "-" + dayOfMonth;

                AlertDialog.Builder builder = new AlertDialog.Builder(OngoingTreatmentActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to change your date ?");
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO : Call API on Yes click

                        date = Date1;

                        Log.i("date", date);

                        getdata();
                    }
                });

                builder.setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        getdata1();

    }

    public void getdata() {
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
                    JSONObject jj = obj.getJSONObject("data");

                    String treatment_date = jj.getString("next_treatment_date");
                    String treatment_status = jj.getString("status");


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
                params.put("treatment_date", date);
                params.put("status", "Pending");
                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/patient_treatment", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }


    public void getdata1() {
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
                        treatment_date = c.getString("treatment_date");
                        treatment_status = c.getString("status");

                    }

                    t_date.setText(treatment_date);
                    editor.putString("s_date", treatment_date);
                    editor.apply();
                    editor.commit();

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
                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/get_patient_treatment_list", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

//    public void data()
//    {
//        try {
//            for (int i = 2; i<= 16; i++)
//            {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                c.setTime(sdf.parse(date));
//                c.add(Calendar.DATE, 3);
//
//                Date resultdate = new Date(c.getTimeInMillis());
//
//                String dd = sdf.format(resultdate);
//
//                Log.i("date",dd);
//
//                sdf = new SimpleDateFormat("EEEE");
//
//                String day = sdf.format(resultdate);
//
//                Log.i("day",day);
//
//
//                CalendarModelTreatment calendarModelTreatment = new CalendarModelTreatment(dd,day,i);
//                calendarModelTreatments.add(calendarModelTreatment);
//
//                date = dd;
//
//            }
//
//            recyclerAdapter = new RecyclerAdapter(calendarModelTreatments,OngoingTreatmentActivity.this);
//            recyclerView.setAdapter(recyclerAdapter);
//
//        }
//        catch (ParseException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);

    }


    public void getdata_completed(String dateee) {
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
                    JSONObject jj = obj.getJSONObject("data");

                    String treatment_date = jj.getString("next_treatment_date");
                    String treatment_status = jj.getString("status");


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
                params.put("treatment_date", dateee);
                params.put("status", "Completed");
                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/patient_treatment", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }


    public void getdata_skipped(String dateee) {
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
                    JSONObject jj = obj.getJSONObject("data");

                    String treatment_date = jj.getString("next_treatment_date");
                    String treatment_status = jj.getString("status");


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
                params.put("treatment_date", dateee);
                params.put("status", "Skip");
                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/patient_treatment", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private List<CalendarModelTreatment> mListenerList;
        Context mContext;
        LayoutInflater inflater;

        public RecyclerAdapter(List<CalendarModelTreatment> mListenerList, Context mContext) {
            this.mListenerList = mListenerList;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView date, day, number;
            ImageView imageView;

            public MyViewHolder(View view) {
                super(view);
                date = (TextView) view.findViewById(R.id.date);
                day = (TextView) view.findViewById(R.id.treatment_day);
                number = (TextView) view.findViewById(R.id.treatment_number);
                imageView = (ImageView) view.findViewById(R.id.imageview);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(OngoingTreatmentActivity.this);

                        builder.setMessage("Did you complete your treatment?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                        gridView.clearChoices();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();


                    }
                });

            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.treatmen_date, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.date.setText(mListenerList.get(position).getDate());
            holder.day.setText(mListenerList.get(position).getDay());
            holder.number.setText("" + mListenerList.get(position).getNumber());

        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

    }



    public void refresh(View view) {          //refresh is onClick name given to the button
        onRestart();
    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(OngoingTreatmentActivity.this, OngoingTreatmentActivity.class);  //your class
        startActivity(i);
        finish();

    }

}