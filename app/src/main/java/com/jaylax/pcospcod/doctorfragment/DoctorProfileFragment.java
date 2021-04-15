package com.jaylax.pcospcod.doctorfragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.RequestHandler;
import com.jaylax.pcospcod.util.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class DoctorProfileFragment extends Fragment {

    TextView submit;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String value;
    EditText fname, lname, specialization, contact, email, clinic_name, country, pincode, address, about;
    String f_name, l_name, _contact, _email, _specialization, _clinic_name, _country, _pincode, _address, _about, imagestring;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    CircularImageView profile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        fname = (EditText) view.findViewById(R.id.fname);
        lname = (EditText) view.findViewById(R.id.lname);
        specialization = (EditText) view.findViewById(R.id.specialization);
        contact = (EditText) view.findViewById(R.id.contact);
        email = (EditText) view.findViewById(R.id.email);
        clinic_name = (EditText) view.findViewById(R.id.clinic_name);
        country = (EditText) view.findViewById(R.id.country);
        pincode = (EditText) view.findViewById(R.id.pincode);
        address = (EditText) view.findViewById(R.id.address);
        submit = (TextView) view.findViewById(R.id.bt_submit);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        about = (EditText) view.findViewById(R.id.about_yourself);
        profile = (CircularImageView) view.findViewById(R.id.profilePic);

        sharedPreferences = getContext().getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_name = fname.getText().toString();
                l_name = lname.getText().toString();
                _specialization = specialization.getText().toString();
                _email = email.getText().toString();
                _contact = contact.getText().toString();
                _clinic_name = clinic_name.getText().toString();
                _country = country.getText().toString();
                _pincode = pincode.getText().toString();
                _address = address.getText().toString();
                _about = about.getText().toString();

                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedID);

                value = radioButton.getText().toString();

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
                        Log.i("response", s);
                        try
                        {
                            Log.d("response", s);
                            JSONObject obj = new JSONObject(s);
//
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                            dialog.setContentView(R.layout.dialog_info_doctor);
                            dialog.setCancelable(true);

                            ImageView icon = dialog.findViewById(R.id.icon_only);

                            icon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

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

                        params.put("userid", user_id);
                        params.put("country_code", _country);
                        params.put("address", _address);
                        params.put("profile_image_url", imagestring);
                        params.put("email", _email);
                        params.put("pin_code", _pincode);
                        params.put("gender", value);
                        params.put("specialization", _specialization);
                        params.put("clinic_hospital_name", _clinic_name);
                        params.put("hospital_address", _address);
                        params.put("description", _about);

                        return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/doctor_data", params);

                    }

                }

                UserLogin ul = new UserLogin();
                ul.execute();
            }
        });

        return view;
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

        profile.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                byte[] imageBytes = bytes.toByteArray();
                imagestring = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.i("image",imagestring);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profile.setImageBitmap(bm);
    }

}