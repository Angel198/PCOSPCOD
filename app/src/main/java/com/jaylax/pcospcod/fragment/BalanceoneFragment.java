package com.jaylax.pcospcod.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.OngoingTreatmentActivity;
import com.jaylax.pcospcod.util.CalendarModelTreatment;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BalanceoneFragment extends Fragment {

    RecyclerView recyclerView;
    TextView textView;
    RecyclerAdapter recyclerAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    LinearLayout layout;
    ArrayList<CalendarModelTreatment> calendarModelTreatments = new ArrayList<>();
    String day;
    ArrayList id = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balanceone, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layout = (LinearLayout) view.findViewById(R.id.layout);

//        textView = (TextView) view.findViewById(R.id.text);

        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        editor = sharedPreferences.edit();


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        getdata();

//
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

                        String t_date = c.getString("treatment_date");
                        String t_id = c.getString("treatment_id");

                        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

                        id.add(t_id);
                        try {
                            Date d1 = sdf.parse(t_date);
                            sdf = new SimpleDateFormat("EEEE");
                            day = sdf.format(d1);

                            Log.i("r_date",t_date);
                            Log.i("day",day);

                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }

                        CalendarModelTreatment calendarModelTreatment = new CalendarModelTreatment(t_date,day,Integer.parseInt(t_id));
                        calendarModelTreatments.add(calendarModelTreatment);
                    }

                    if (id.size() == 0)
                    {
                        layout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        layout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    recyclerAdapter = new RecyclerAdapter(calendarModelTreatments,getContext());
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

                params.put("patient_id", user_id);
                params.put("status", "Completed");
                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/get_balanced_treatment", params);

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
            TextView date, day , number;
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


                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
            holder.number.setText(""+mListenerList.get(position).getNumber());

        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

    }

}