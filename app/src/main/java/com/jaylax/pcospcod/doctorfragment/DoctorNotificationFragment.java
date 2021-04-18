package com.jaylax.pcospcod.doctorfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.NotificationModel;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorNotificationFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token, P_ID, P_AGE;
    ArrayList id = new ArrayList();
    ArrayList<NotificationModel> notificationModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_notification, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        linearlayout = (LinearLayout) findViewById(R.id.layout);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        sharedPreferences = getContext().getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);


        getdata();

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

                        String a = c.getString("name");
                        String b = c.getString("id");
                        String d = c.getString("message");
//                        String e = c.getString("age");
//                        String f = c.getString("mobile_number");
//                        String g = c.getString("profile_image_url");
//
//                        String _name = a + " " +b;

                        id.add(d);

                        NotificationModel patientInquiryModel = new NotificationModel(b,a,d);
                        notificationModels.add(patientInquiryModel);

                    }
//                    if (id.size() == 0)
//                    {
//                        linearlayout.setVisibility(View.VISIBLE);
//                        recyclerView.setVisibility(View.GONE);
//                    }
//                    else {
//                        linearlayout.setVisibility(View.GONE);
//                        recyclerView.setVisibility(View.VISIBLE);
//                    }
                    recyclerAdapter = new RecyclerAdapter(notificationModels,getContext());
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

                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/get_doctor_notification", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private List<NotificationModel> mListenerList;
        Context mContext;
        LayoutInflater inflater;

        public RecyclerAdapter(List<NotificationModel> mListenerList, Context mContext) {
            this.mListenerList = mListenerList;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, msg , number;
//            CircularImageView circularImageView;
//            LinearLayout layout_re;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.name);
                msg = (TextView) view.findViewById(R.id.book);
//                number = (TextView) view.findViewById(R.id.patient_number);
//                circularImageView = (CircularImageView) view.findViewById(R.id.profilePic);
//                layout_re = (LinearLayout) view.findViewById(R.id.layout_re);
            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification_layout, parent, false);
            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.name.setText(mListenerList.get(position).getName());
            holder.msg.setText(mListenerList.get(position).getMsg());
//            holder.number.setText(""+mListenerList.get(position).getPatient_contact());

//            byte[] decodestring = Base64.decode(mListenerList.get(position).getImage(),Base64.DEFAULT);
//            Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

    }

}