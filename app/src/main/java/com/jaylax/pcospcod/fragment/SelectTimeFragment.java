package com.jaylax.pcospcod.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.MySingleton;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class SelectTimeFragment extends Fragment {

    private TimePicker timePicker1;
    private Calendar calendar;
    DatePicker picker;
    private String format = "";
    String time;
    String date;
    TextView next;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String fname, lname, email,contact, state, district,city, pincode, address, symptoms,weight,height, age, birth_date,status, p_one, p_two, p_three,
            p_four, p_five, p_six, p_Seven , p_eight, p_nine, p_ten, p_eleven, p_twelve, p_test, p_doctor;
    String selected_date_time;
    String user_id, doctor;
    SharedPreferences preferences;
    SharedPreferences.Editor editor1;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAA2nU0-ms:APA91bFBAynRSQJKPbzIgJHT5C4JN0lLrNraFipvUWxxoKg1AEY3bgs0vPK3pvIWJE1tiSFe8mSm8o0ByUj85DdSmV3uUZAZqtv2g0uL28PDEBq99RvfsDYDwOtWFTagfDzr1p1GFNEX";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_time, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        fname = sharedPreferences.getString("i_name",null);
        lname = sharedPreferences.getString("i_lname",null);
        email = sharedPreferences.getString("i_email",null);
        contact = sharedPreferences.getString("i_number",null);
        city = sharedPreferences.getString("i_village",null);
        pincode = sharedPreferences.getString("i_pin",null);
        address = sharedPreferences.getString("i_street",null);
        symptoms = sharedPreferences.getString("i_symptoms",null);
        weight = sharedPreferences.getString("i_weight",null);
        height = sharedPreferences.getString("i_height",null);
        age = sharedPreferences.getString("i_age",null);
        birth_date = sharedPreferences.getString("i_birth",null);
        status = sharedPreferences.getString("i_status",null);
        p_one = sharedPreferences.getString("i_one_value",null);
        p_two = sharedPreferences.getString("i_two_value",null);
        p_three = sharedPreferences.getString("i_three_value",null);
        p_four = sharedPreferences.getString("i_four_value",null);
        p_five = sharedPreferences.getString("i_five_value",null);
        p_six = sharedPreferences.getString("i_six_value",null);
        p_Seven = sharedPreferences.getString("i_seven_value",null);
        p_eight = sharedPreferences.getString("i_eight_value",null);
        p_nine = sharedPreferences.getString("i_nine_value",null);
        p_ten = sharedPreferences.getString("i_ten_value",null);
        p_eleven = sharedPreferences.getString("i_eleven_value",null);
        p_twelve = sharedPreferences.getString("i_twelve_value",null);
        p_test = sharedPreferences.getString("i_test_name",null);
//        p_doctor = sharedPreferences.getString("i_doctor",null);
        doctor = sharedPreferences.getString("i_doctor",null);

        Log.i("ddddd",doctor);

        timePicker1 = (TimePicker) view.findViewById(R.id.timePicker1);
        next = (TextView) view.findViewById(R.id.bt_submit);
        picker=(DatePicker) view.findViewById(R.id.datePicker1);

        preferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        user_id = preferences.getString("userid",null);

        Log.i("ss",user_id);

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay +":" + minute;

                Log.i("time",time);

            }

        });

        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String currentDate = (monthOfYear+1) + "/" + dayOfMonth + "/" + year;
                date = year +"/"+ (monthOfYear+1) +"/" +dayOfMonth;
                Log.i("date",date);

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_date_time = date +"/ "+ time;

                Log.i("dds",selected_date_time);

                editor.putString("i_selected_time",selected_date_time);

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
//                        Log.i("response", s);
                        try {

                            Log.d("response", s);
                            JSONObject obj = new JSONObject(s);
                            JSONObject jj = obj.getJSONObject("data");

                            String user = jj.getString("user_type");

                            if (!user.equals(""))
                            {
                                final Dialog dialog = new Dialog(getContext());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                                dialog.setContentView(R.layout.dialog_info);
                                dialog.setCancelable(true);

                                ImageView icon = dialog.findViewById(R.id.icon_only);
                                Handler handler=new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();

                                        Intent intent = new Intent(getContext(), PatientDashboardActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                },5000);

                                icon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(getContext(), PatientDashboardActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                });

                                dialog.show();

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
                        params.put("first_name", fname);
                        params.put("last_name", lname);
                        params.put("birth_date", birth_date);
                        params.put("country_code", city);
                        params.put("state_code", city);
                        params.put("district_code", city);
                        params.put("taluka_code", city);
                        params.put("village_code", city);
                        params.put("pin_code", pincode);
                        params.put("address", address);
                        params.put("weight", weight);
                        params.put("height", height);
                        params.put("status", status);
                        params.put("please_choose_doctor_near_to_you", doctor);
                        params.put("please_provide_when_doctor_will_contact_you", selected_date_time);
                        params.put("since_how_you_have_pcos_or_pcod", p_one);
                        params.put("are_you_married", status);
                        params.put("when_did_you_had_last_menstrual_cycle", p_two);
                        params.put("you_got_menstrual_cycle", p_three);
                        params.put("how_many_days_you_use_to_have_menstrual_cycle", p_four);
                        params.put("how_was_your_flow_at_the_time_of_menstrual_cycle", p_five);
                        params.put("when_did_you_had_last_time_medicines", p_Seven);
                        params.put("have_you_done", p_eight);
                        params.put("on_which_day_you_were_having_last_menstrual_cycle", p_six);
                        params.put("have_you_tried_to_lose_weight", p_nine);
                        params.put("have_you_tried_to_diet", p_ten);
                        params.put("have_you_tried_to_change_lifestyle", p_eleven);
                        params.put("have_you_got_any_results_in_losing_weight", p_twelve);
                        params.put("email", email);
                        params.put("symptoms",symptoms);

                        Log.i("d","ddd");

                        return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/patient_data", params);
//                        Log.i("ds","ddd");
                    }

                }

                UserLogin ul = new UserLogin();
                ul.execute();


            }

        });

        return view;

    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
//                        edtTitle.setText("");
//                        edtMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

}