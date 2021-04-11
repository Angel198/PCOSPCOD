package com.jaylax.pcospcod.doctorfragment;

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
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientProfileActivity;
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


public class DoctorProfileViewFragment extends Fragment {

    TextView name, email, contact, specialization, clinic_name, clinic_address, gender;
    CircularImageView profile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String imagestring;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, _name, token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile_view, container, false);

        name = (TextView) view.findViewById(R.id.d_name);
        email = (TextView) view.findViewById(R.id.d_email);
        contact = (TextView) view.findViewById(R.id.d_contact);
        specialization = (TextView) view.findViewById(R.id.d_specialization);
        clinic_address = (TextView) view.findViewById(R.id.d_clinic_address);
        clinic_name = (TextView) view.findViewById(R.id.d_clinic_name);
        profile = (CircularImageView) view.findViewById(R.id.profilePic);
        gender = (TextView) view.findViewById(R.id.d_gender);

        sharedPreferences = getContext().getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        _name = sharedPreferences.getString("nn",null);

        name.setText(_name);

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


                        email.setText(em);
                        contact.setText(k);
                        profile.setImageBitmap(decodeByte);
                        gender.setText(b);
                        specialization.setText(st);
                        clinic_name.setText(st1);
                        clinic_address.setText(e);


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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(getContext());

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
        return ContextCompat.checkSelfPermission(getContext(),
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
//        uploadimage();

        profile.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;

        if (data != null) {
            try {

                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                byte[] imageBytes = bytes.toByteArray();
                imagestring = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.i("image",imagestring);

//                uploadimage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profile.setImageBitmap(bm);

    }
}