package com.jaylax.pcospcod.doctoractivities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.VideoCalling.BaseActivity;
import com.jaylax.pcospcod.VideoCalling.CallScreenActivity;
import com.jaylax.pcospcod.VideoCalling.SinchService;
import com.jaylax.pcospcod.util.ReportModel;
import com.jaylax.pcospcod.util.RequestHandler;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PatientDetailActivity extends BaseActivity implements SinchService.StartFailedListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, _name, token, P_ID, P_AGE;
    TextView available, name,number, birth, age, weight, email, height, status, location, patient_pcos_pcod,
            patient_last_cycle, patient_menstrual_cycle, patient_menstrual_cycle_day, patient_flow_menstrual_cycle_day,
            patient_last_time_medicines, patient_done, patient_which_Day_last, patient_loose_weight, patient_try_diet,
            patient_try_lifestyle, result_in_losing_weight,tv_call;
    ArrayList<ReportModel> reportModels = new ArrayList<>();
    ArrayList re_pdf = new ArrayList();
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        initToolbar();

        sharedPreferences = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        _name = sharedPreferences.getString("nn",null);

        P_ID = getIntent().getStringExtra("patient_ID");
        P_AGE = getIntent().getStringExtra("patient_Age");

        available = (TextView) findViewById(R.id.patient_available);
        name = (TextView) findViewById(R.id.patient_name);
        number = (TextView) findViewById(R.id.patient_number);
        birth = (TextView) findViewById(R.id.patient_birth);
        age = (TextView) findViewById(R.id.patient_age);
        weight = (TextView) findViewById(R.id.patient_weight);
        email = (TextView) findViewById(R.id.patient_email);
        height = (TextView) findViewById(R.id.patient_height);
        status = (TextView) findViewById(R.id.patient_status);
        location = (TextView) findViewById(R.id.patient_location);
        patient_pcos_pcod = (TextView) findViewById(R.id.patient_pcos_pcod);
        patient_last_cycle = (TextView) findViewById(R.id.patient_last_cycle);
        patient_menstrual_cycle = (TextView) findViewById(R.id.patient_menstrual_cycle);
        patient_menstrual_cycle_day = (TextView) findViewById(R.id.patient_menstrual_cycle_day);
        patient_flow_menstrual_cycle_day = (TextView) findViewById(R.id.patient_flow_menstrual_cycle_day);
        patient_last_time_medicines = (TextView) findViewById(R.id.patient_last_time_medicines);
        patient_done = (TextView) findViewById(R.id.patient_done);
        patient_which_Day_last = (TextView) findViewById(R.id.patient_which_Day_last);
        patient_loose_weight = (TextView) findViewById(R.id.patient_loose_weight);
        patient_try_diet = (TextView) findViewById(R.id.patient_try_diet);
        patient_try_lifestyle = (TextView) findViewById(R.id.patient_try_lifestyle);
        result_in_losing_weight = (TextView) findViewById(R.id.result_in_losing_weight);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

//        tv_call = (TextView) findViewById(R.id.tv_call);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
        }
        getdata();

        age.setText(P_AGE);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

//        tv_call.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!getSinchServiceInterface().isStarted()) {
//                    getSinchServiceInterface().startClient(user_id);
////                showSpinner();
//                } else {
////                openPlaceCallActivity();
//                    callButtonClicked();
//                }
//            }
//        } );

        getpatientreport();

    }


    private void loginClicked() {
        String userName = user_id;

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);

        } else {

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

        MenuItem menu_item = menu.findItem(R.id.action_video);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
        }
        else if (id == R.id.action_video)
        {
            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(user_id);
//                showSpinner();
            } else {
//                openPlaceCallActivity();
                callButtonClicked();
            }

//            Intent intent = new Intent(PatientDetailActivity.this, DoctorVideoCallActivity.class);
//            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
    private void callButtonClicked() {
        String userName = P_ID;
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
    private void callButtonAudioClicked() {
        String userName = P_ID;
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Call call = getSinchServiceInterface().callUser(userName);
            if (call == null) {
                // Service failed for some reason, show a Toast and abort
                Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
                        + "placing a call.", Toast.LENGTH_LONG).show();
                return;
            }
            String callId = call.getCallId();
            Intent callScreen = new Intent(this, CallScreenActivity.class);
            callScreen.putExtra(SinchService.CALL_ID, callId);
            startActivity(callScreen);
        } catch (MissingPermissionException e) {
            ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
        }

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

                        String a = c.getString("weight");
                        String b = c.getString("height");
                        String d = c.getString("status");
                        String e = c.getString("symptoms");
                        String f = c.getString("please_choose_doctor_near_to_you");
                        String g = c.getString("please_provide_when_doctor_will_contact_you");
                        String h = c.getString("since_how_you_have_pcos_or_pcod");
                        String j = c.getString("are_you_married");
                        String k = c.getString("when_did_you_had_last_menstrual_cycle");
                        String l = c.getString("you_got_menstrual_cycle");
                        String m = c.getString("how_many_days_you_use_to_have_menstrual_cycle");
                        String n = c.getString("how_was_your_flow_at_the_time_of_menstrual_cycle");
                        String o = c.getString("when_did_you_had_last_time_medicines");
                        String p = c.getString("have_you_done");
                        String q = c.getString("on_which_day_you_were_having_last_menstrual_cycle");
                        String r = c.getString("have_you_tried_to_lose_weight");
                        String t = c.getString("have_you_tried_to_diet");
                        String u = c.getString("have_you_tried_to_change_lifestyle");
                        String v = c.getString("have_you_got_any_results_in_losing_weight");
                        String w = c.getString("created_at");
                        String x = c.getString("first_name");
                        String y = c.getString("last_name");
                        String z = c.getString("email");
                        String z1 = c.getString("mobile_number");
                        String z2 = c.getString("city");

                        String _n = x +" "+y;

                        available.setText(g);
                        name.setText(_n);
                        number.setText(z1);
                        location.setText(z2);
                        weight.setText(a);
                        height.setText(b);
                        status.setText(d);
                        email.setText(z);
                        patient_which_Day_last.setText(q);
                        patient_last_cycle.setText(k);
                        patient_pcos_pcod.setText(h);
                        patient_menstrual_cycle.setText(l);
                        patient_menstrual_cycle_day.setText(m);
                        patient_flow_menstrual_cycle_day.setText(n);
                        patient_last_time_medicines.setText(o);
                        patient_done.setText(p);
                        patient_try_diet.setText(t);
                        patient_try_lifestyle.setText(u);
                        patient_loose_weight.setText(v);
                        loginClicked();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("doctor_id", user_id);
                params.put("patient_id", P_ID);
                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/patient_inquiry_details", params);

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


    public void getpatientreport()
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
                        String a = c.getString("patient_id");
                        String b = c.getString("report");
                        String d = c.getString("report_name");

                        re_pdf.add(d);
                        String e = "http://curepcos.in/hospitalmanagement/uploads/"+d;
                        ReportModel reportModel = new ReportModel(a,d,e);
                        reportModels.add(reportModel);
                    }


                    Log.i("size", String.valueOf(re_pdf.size()));

                    recyclerAdapter = new RecyclerAdapter(reportModels,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("doctor_id", user_id);
                params.put("patient_id", P_ID);
                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/patient_reports_details", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private List<ReportModel> mListenerList;
        Context mContext;
        LayoutInflater inflater;

        public RecyclerAdapter(List<ReportModel> mListenerList, Context mContext) {
            this.mListenerList = mListenerList;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            ImageView down_load;
            PDFView pdfView;
            WebView webview;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.r_name);
                down_load = (ImageView) view.findViewById(R.id.t_download);
                webview = (WebView) view.findViewById(R.id.pdfView);


            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_layout, parent, false);
            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.name.setText(mListenerList.get(position).getReport_name());

            String url = "http://drive.google.com/viewerng/viewer?embedded=true&url="+mListenerList.get(position).getReport();
            holder.webview.getSettings().setJavaScriptEnabled(true);
            holder.webview.loadUrl(url);

        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

    }


}