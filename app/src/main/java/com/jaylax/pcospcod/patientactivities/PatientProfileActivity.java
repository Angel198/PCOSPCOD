package com.jaylax.pcospcod.patientactivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patienteditprofile.EditPatientContactFragment;
import com.jaylax.pcospcod.patienteditprofile.EditPatientEmailFragment;
import com.jaylax.pcospcod.patienteditprofile.EditPatientHeightFragment;
import com.jaylax.pcospcod.patienteditprofile.EditPatientStatusFragment;
import com.jaylax.pcospcod.patienteditprofile.EditPatientWeightFragment;
import com.jaylax.pcospcod.util.RequestHandler;
import com.jaylax.pcospcod.util.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PatientProfileActivity extends AppCompatActivity {

    TextView contact,email,status, height, weight, age, birth_Date, p_name;
    FrameLayout frameLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    String _height, _weight,imagestring;
    CircularImageView profile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        initToolbar();

        contact = (TextView) findViewById(R.id.p_contact);
        email = (TextView) findViewById(R.id.p_email);
        status = (TextView) findViewById(R.id.p_marital_status);
        height = (TextView) findViewById(R.id.p_height);
        weight = (TextView) findViewById(R.id.p_weight);
        age = (TextView) findViewById(R.id.p_age);
        birth_Date = (TextView) findViewById(R.id.p_birthdate);
        p_name = (TextView) findViewById(R.id.p_name);
        frameLayout = (FrameLayout) findViewById(R.id.Framelayout);
        profile = (CircularImageView) findViewById(R.id.profilePic);

        setvisibility();

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);

        p_name.setText(name);

        getdata();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.Framelayout, new EditPatientContactFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.Framelayout, new EditPatientEmailFragment());
                ft.addToBackStack(null);
                ft.commit();

            }

        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.Framelayout, new EditPatientStatusFragment());
                ft.addToBackStack(null);
                ft.commit();

            }

        });

//        age.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                frameLayout.setVisibility(View.VISIBLE);
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.Framelayout, new EditPatientAgeFragment());
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });


        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putString("weight", _weight);
                EditPatientWeightFragment fragobj = new EditPatientWeightFragment();
                fragobj.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.Framelayout, new EditPatientWeightFragment());
                ft.addToBackStack(null);
                ft.commit();

            }

        });


        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameLayout.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.Framelayout, new EditPatientHeightFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

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

    public void setvisibility() {
        frameLayout.setVisibility(View.GONE);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
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

                        String a = c.getString("age");
                        String b = c.getString("mobile_number");
                        String d = c.getString("city");
                        String e = c.getString("first_name");
                        String f = c.getString("last_name");
                        String g = c.getString("birth_date");
                        String h = c.getString("country_code");
                        String j = c.getString("pin_code");
                        String k = c.getString("address");
                        _weight = c.getString("weight");
                        _height = c.getString("height");
                        String image = c.getString("profile_image_url");
                        String em = c.getString("email");
                        String st = c.getString("status");


                        byte[] decodestring = Base64.decode(image,Base64.DEFAULT);
                        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

                        contact.setText(b);
                        height.setText(_height);
                        age.setText(a +" Yr");
                        weight.setText(_weight +" Kg");
                        birth_Date.setText(g);
                        profile.setImageBitmap(decodeByte);
                        email.setText(em);
                        status.setText(st);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PatientProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(PatientProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
//                    if(result)

                    if(hasCameraPermission()) {
//                            pickImage();
                        cameraIntent();

                    }
                    else
                    {
                        requestCameraPermission();
                    }
//                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(PatientProfileActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA );
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte[] imageBytes = bytes.toByteArray();
        imagestring = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.i("image",imagestring);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        uploadimage();

        profile.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;

        if (data != null) {
            try {

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                byte[] imageBytes = bytes.toByteArray();
                imagestring = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.i("image",imagestring);

                uploadimage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profile.setImageBitmap(bm);

    }

    public void uploadimage()
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
                Log.d("response", s);

                try {

                    Log.d("response", s);

                    JSONObject obj = new JSONObject(s);
                    String rs = obj.getString("success");


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
                params.put("profile_image_url",imagestring);

                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/update_patient_profile", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }
}