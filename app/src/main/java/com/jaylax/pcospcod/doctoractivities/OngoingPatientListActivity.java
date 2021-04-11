package com.jaylax.pcospcod.doctoractivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.PatientInquiryModel;
import com.jaylax.pcospcod.util.RequestHandler;
import com.mikhaellopez.circularimageview.CircularImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OngoingPatientListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token, P_ID, P_AGE;
    ArrayList<PatientInquiryModel> patientInquiryModelArrayList = new ArrayList<>();
    LinearLayout linearlayout;
    ArrayList id = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_patient_list);

        initToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearlayout = (LinearLayout) findViewById(R.id.layout);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        sharedPreferences = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);

//        P_ID = getIntent().getStringExtra("patient_ID");
//        P_AGE = getIntent().getStringExtra("patient_Age");


        getdata();

//        if (id.size() == 0)
//        {
//            linearlayout.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
//        else {
//            linearlayout.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }


    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home )
        {
            finish();
        }

        return super.onOptionsItemSelected(item);

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

                        String a = c.getString("first_name");
                        String b = c.getString("last_name");
                        String d = c.getString("patient_id");
                        String e = c.getString("age");
                        String f = c.getString("mobile_number");
                        String g = c.getString("profile_image_url");

                        String _name = a + " " +b;

                        id.add(d);
                        PatientInquiryModel patientInquiryModel = new PatientInquiryModel(d,_name,f,e,g);
                        patientInquiryModelArrayList.add(patientInquiryModel);

                    }
                    if (id.size() == 0)
                    {
                        linearlayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        linearlayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    recyclerAdapter = new RecyclerAdapter(patientInquiryModelArrayList,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);

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
                params.put("doctor_id", user_id);

                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/total_Ongoing_patient", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private List<PatientInquiryModel> mListenerList;
        Context mContext;
        LayoutInflater inflater;

        public RecyclerAdapter(List<PatientInquiryModel> mListenerList, Context mContext) {
            this.mListenerList = mListenerList;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, age , number;
            CircularImageView circularImageView;
            LinearLayout layout_re;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.patient_name);
                age = (TextView) view.findViewById(R.id.patient_age);
                number = (TextView) view.findViewById(R.id.patient_number);
                circularImageView = (CircularImageView) view.findViewById(R.id.profilePic);
                layout_re = (LinearLayout) view.findViewById(R.id.layout_re);


            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inquiry_patient, parent, false);
            return new RecyclerAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.name.setText(mListenerList.get(position).getPatient_name());
            holder.age.setText(mListenerList.get(position).getPatient_age()+" Yr");
            holder.number.setText(""+mListenerList.get(position).getPatient_contact());

            byte[] decodestring = Base64.decode(mListenerList.get(position).getImage(),Base64.DEFAULT);
            Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

            holder.circularImageView.setImageBitmap(decodeByte);

            holder.layout_re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OngoingPatientListActivity.this,OngoingPatientTreatmentActivity.class);
                    intent.putExtra("patient_ID",mListenerList.get(position).getPatient_id());
                    intent.putExtra("patient_Age",mListenerList.get(position).getPatient_age());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

    }


}