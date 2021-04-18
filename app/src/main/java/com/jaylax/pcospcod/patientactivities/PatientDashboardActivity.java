package com.jaylax.pcospcod.patientactivities;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.fragment.AboutusFragment;
import com.jaylax.pcospcod.fragment.ChatFragment;
import com.jaylax.pcospcod.fragment.ConditionFragment;
import com.jaylax.pcospcod.fragment.ContactFragment;
import com.jaylax.pcospcod.fragment.HomeFragment;
import com.jaylax.pcospcod.fragment.InquiryFragment;
import com.jaylax.pcospcod.fragment.NotificationFragment;
import com.jaylax.pcospcod.fragment.ProfileFragment;
import com.jaylax.pcospcod.fragment.TestimonialFragment;
import com.jaylax.pcospcod.util.RequestHandler;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PatientDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    CircularImageView imageView;
    TextView remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        DrawerLayout drawer = findViewById(R.id.drawer_layout) ;
//        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

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
        remove = (TextView) view.findViewById(R.id.remove);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        name_header.setText(name);

        displaySelectedScreen(R.id.nav_home);

        setToolbarTitle("Home");

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PatientDashboardActivity.this,PatientProfileActivity.class);
                startActivity(intent);
            }
        });

        getdata();

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.Framelayout, new HomeFragment());
//        ft.addToBackStack(null);
//        ft.commit();


//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//
//                switch (item.getItemId()) {
//
//                    case R.id.home:
//                        selectedFragment = new HomeFragment();
//                        break;
//                    case R.id.testimonial:
//                        selectedFragment = new TestimonialFragment();
//                        break;
//
//                    case R.id.notification:
//                        selectedFragment = new NotificationFragment();
//                        break;
//
//                    case R.id.chat:
//                        selectedFragment = new ChatFragment();
//                        break;
//
//                }
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.Framelayout, selectedFragment)
//                        .addToBackStack(null)
//                        .commit();
//
//                return true;
//            }
//
//        });
        
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

                        String a = c.getString("age");
                        String b = c.getString("mobile_number");
                        String d = c.getString("city");
                        String e = c.getString("first_name");
                        String f = c.getString("last_name");
                        String g = c.getString("birth_date");
                        String h = c.getString("country_code");
                        String j = c.getString("pin_code");
                        String k = c.getString("address");
                        String image = c.getString("profile_image_url");
                        String em = c.getString("email");
                        String st = c.getString("status");

                        if (image.equals(""))
                        {

                        }
                        else {
                            byte[] decodestring = Base64.decode(image,Base64.DEFAULT);
                            Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

                            imageView.setImageBitmap(decodeByte);
                            remove.setText("");
                            remove.setVisibility(View.GONE);
                        }

                        editor.putString("edit_contact",b);
                        editor.putString("edit_email",em);
                        editor.apply();
                        editor.commit();


//                        _weight = c.getString("weight");
//                        _height = c.getString("height");
//                        contact.setText(b);
//                        height.setText(_height);
//                        age.setText(a +" Yr");
//                        weight.setText(_weight +" Kg");
//                        birth_Date.setText(g);
//                        p_name.setText(e +" "+f);

                    }

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

                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/patientprofile", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

    @Override
    public void onBackPressed () {

        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START) ;
        } else {
            super .onBackPressed() ;
        }

    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;

            case R.id.nav_availibiity:
                fragment = new InquiryFragment();
                break;

            case R.id.nav_chat:
                fragment = new ChatFragment();
                break;

            case R.id.nav_notification:
                fragment = new NotificationFragment();
                break;

            case R.id.nav_about:
                fragment = new AboutusFragment();
                break;

            case R.id.nav_contact:
                fragment = new ContactFragment();
                break;

            case R.id.nav_terms:
                fragment = new ConditionFragment();
                break;

            case R.id.nav_testimonial:
                fragment = new TestimonialFragment();
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

//    public void setVisibility() {
//
//        bottomNavigationView.setVisibility(View.GONE);
//
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }


}