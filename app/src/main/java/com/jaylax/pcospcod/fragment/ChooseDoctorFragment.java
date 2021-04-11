package com.jaylax.pcospcod.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.SelectDoctorModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChooseDoctorFragment extends Fragment {

    TextView next;
    String value, _val;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CircularImageView one, two, three;
    LinearLayout l_one, l_two;
    GridView gridView;
    ArrayList<SelectDoctorModel> selectDoctorModels = new ArrayList<>();
    GridAdapter gridAdapter;
    ArrayList d_id = new ArrayList();
    ArrayList Name = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_doctor, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Consult a Doctor");

        sharedPreferences = getContext().getSharedPreferences(InquiryFragment.MyPREFERENCES_TEMP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        next = (TextView) view.findViewById(R.id.bt_submit);
        gridView = (GridView) view.findViewById(R.id.gridview);


        getdoctor("http://pcospcod.curepcos.in/api/getdoctor");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                value = String.valueOf(d_id.get(position));

                Log.i("value",value);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.putString("i_doctor_name",_val);
                editor.putString("i_doctor",_val);
                editor.apply();
                editor.commit();

                ((PatientDashboardActivity)getActivity()).replaceFragment(new SelectTimeFragment());

            }
        });


        return view;
    }


    private void getdoctor(final String urlWebService) {

        class GetCATEGORY extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.i("response",s);
//
//                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

                try {

                    Log.i("response",s);
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); ++i) {

                        JSONObject c = jsonArray.getJSONObject(i);

                        String a = c.getString("id");
                        String b = c.getString("name");
                        String d = c.getString("specialization");
                        String h = c.getString("profile_image_url");


                        d_id.add(a);

                        SelectDoctorModel selectDoctorModel = new SelectDoctorModel(a,b,d,h);
                        selectDoctorModels.add(selectDoctorModel);

                    }

                    gridAdapter = new GridAdapter(getContext(),R.layout.choose_doctor,selectDoctorModels);
                    gridView.setAdapter(gridAdapter);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetCATEGORY getJSON = new GetCATEGORY();
        getJSON.execute();
    }


    public  class GridAdapter extends ArrayAdapter<SelectDoctorModel> {

        ArrayList<SelectDoctorModel> productList;
        ArrayList<SelectDoctorModel> tempCustomer;
        ArrayList<SelectDoctorModel> suggestions;

        Context context;
        int resource;
        public GridAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SelectDoctorModel> productList) {
            super(context, resource, productList);
            this.productList=productList;
            this.context=context;
            this.resource=resource;
            this.tempCustomer =new ArrayList<SelectDoctorModel>(productList);
            this.suggestions =new ArrayList<SelectDoctorModel>(productList);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView==null){
                LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView=layoutInflater.inflate(R.layout.choose_doctor,null,true);
            }
            final SelectDoctorModel pr = getItem(position);

            CircularImageView imageView = (CircularImageView) convertView.findViewById(R.id.one);
            TextView name = (TextView)convertView.findViewById(R.id.d_name);
            TextView specialization = (TextView)convertView.findViewById(R.id.d_specialization);
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.l_one);


            byte[] decodestring = Base64.decode(pr.getImage(),Base64.DEFAULT);
            Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

            imageView.setImageBitmap(decodeByte);
            name.setText(pr.getName());
            specialization.setText(pr.getSpecialization());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    layout.setBackgroundResource(R.color.teal_400);
                    _val = pr.getId();
                    Log.i("val",_val);

                }
            });

            return convertView;

        }
    }
}