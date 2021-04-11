package com.jaylax.pcospcod.doctoractivities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.doctorfragment.DoctorAboutFragment;
import com.jaylax.pcospcod.doctorfragment.DoctorChatFragment;
import com.jaylax.pcospcod.doctorfragment.DoctorContactFragment;
import com.jaylax.pcospcod.doctorfragment.DoctorHomeFragment;
import com.jaylax.pcospcod.doctorfragment.DoctorNotificationFragment;
import com.jaylax.pcospcod.doctorfragment.DoctorTestimonialFragment;
import com.jaylax.pcospcod.fragment.AboutusFragment;
import com.jaylax.pcospcod.fragment.ContactFragment;
import com.jaylax.pcospcod.fragment.TestimonialFragment;
import com.jaylax.pcospcod.util.RequestHandler;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class DoctorDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    CircularImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        DrawerLayout drawer = findViewById(R.id.drawer_layout) ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.openDrawer(GravityCompat.START);
            }
        });

        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        NavigationView navigationView = findViewById(R.id.navigationview) ;
        navigationView.setNavigationItemSelectedListener(this) ;

        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView name_header = (TextView) view.findViewById(R.id.name);
        TextView edit_profile = (TextView) view.findViewById(R.id.textView);
        imageView = (CircularImageView) view.findViewById(R.id.nav_image);

        sharedPreferences = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);

        name_header.setText(name);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorDashboardActivity.this,DoctorProfileViewActivity.class);
                startActivity(intent);
            }
        });

        displaySelectedScreen(R.id.nav_home);

        setToolbarTitle("Home");

        getdata();
    }
    @Override
    public void onBackPressed () {

        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START) ;
        } else {
            super .onBackPressed() ;
//            finish();
        }

    }

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new DoctorHomeFragment();
                break;

//            case R.id.nav_profile:
//                fragment = new DoctorProfileFragment();
//                break;
//
//            case R.id.nav_availibiity:
//                fragment = new PatientFragment();
//                break;

            case R.id.nav_chat:
                fragment = new DoctorChatFragment();
                break;

            case R.id.nav_notification:
                fragment = new DoctorNotificationFragment();
                break;

            case R.id.nav_about:
                fragment = new DoctorAboutFragment();
                break;

            case R.id.nav_contact:
                fragment = new DoctorContactFragment();
                break;

            case R.id.nav_testimonial:
                fragment = new DoctorTestimonialFragment();
                break;

//            case R.id.nav_logout:
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setMessage("Do you want to delete all?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
////                                Intent intent = new Intent(PatientDashboardActivity.this,MainActivity.class);
////                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                startActivity(intent);
////                                finish();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.Framelayout, fragment);
            ft.addToBackStack(null);
                ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Framelayout, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displaySelectedScreen(item.getItemId());
        return true;

    }

    public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
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
                    JSONObject jj = obj.getJSONObject("data");

                    String a = jj.getString("name");
                    String b = jj.getString("gender");
                    String d = jj.getString("city");
                    String e = jj.getString("hospital_address");
                    String h = jj.getString("address");
                    String j = jj.getString("city");
                    String k = jj.getString("mobile_number");
                    String image = jj.getString("profile_image_url");
                    String em = jj.getString("email");
                    String st = jj.getString("specialization");
                    String st1 = jj.getString("clinic_hospital_name");

                    byte[] decodestring = Base64.decode(image,Base64.DEFAULT);
                    Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

                    imageView.setImageBitmap(decodeByte);
//
//                    email.setText(em);
//                    contact.setText(k);
//                    profile.setImageBitmap(decodeByte);
//                    gender.setText(b);
//                    specialization.setText(st);
//                    clinic_name.setText(st1);
//                    clinic_address.setText(e);


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
                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/doctorprofile", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }
}