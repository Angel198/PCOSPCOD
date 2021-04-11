package com.jaylax.pcospcod.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.patientactivities.BalancedTreatmentActivity;
import com.jaylax.pcospcod.patientactivities.MyDoctorActivity;
import com.jaylax.pcospcod.patientactivities.OngoingTreatmentActivity;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.ReportActivity;
import com.jaylax.pcospcod.util.RequestHandler;
import com.jaylax.pcospcod.util.Uploadd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    LinearLayout layout_one, layout_two, layout_three, layout_four;
    TextView upload_file, upload, upload_filE_name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    String FileName, PdfID, encodepdf;
    String PathHolder;
    private int REQ_PDF = 21;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("History");

        layout_one = (LinearLayout) view.findViewById(R.id.layout_one);
        layout_two = (LinearLayout) view.findViewById(R.id.layout_two);
        layout_three = (LinearLayout) view.findViewById(R.id.layout_three);
        layout_four = (LinearLayout) view.findViewById(R.id.layout_four);
//        upload_file = (TextView) view.findViewById(R.id.upload_file);
//        upload = (TextView) view.findViewById(R.id.upload_filee);
//        upload_filE_name = (TextView) view.findViewById(R.id.upload_file_name);


        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        editor = sharedPreferences.edit();

        Log.i("user_id",user_id);


        layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), OngoingTreatmentActivity.class);
                startActivity(intent);
            }
        });

        layout_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), BalancedTreatmentActivity.class);
                startActivity(intent);

            }
        });

        layout_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ReportActivity.class);
                startActivity(intent);

            }
        });

        layout_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MyDoctorActivity.class);
                startActivity(intent);
            }

        });
//
//        upload_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent  intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("application/pdf");
//                intent = Intent.createChooser(intent, "Choose a file");
//                getActivity().startActivityForResult(intent, REQ_PDF);
//
//            }
//        });
//
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                getdata();
//            }
//
//        });

        return view;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PDF && resultCode == 200 && data != null)
        {
            Uri uri = data.getData();
            FileName = getFileName(data.getData());
            Log.i("FileName",FileName);
            PathHolder = getPath( getActivity( ).getApplicationContext( ), uri );
            Log.d("Picture Path", PathHolder);

            File fl = new File(uri.toString());
            String path = fl.getAbsolutePath();
            Log.d("Path", path);

            upload_filE_name.setText(FileName);

            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                byte[] pdfbytes = new byte[inputStream.available()];
                inputStream.read(pdfbytes);
                encodepdf = Base64.encodeToString(pdfbytes, Base64.DEFAULT);

                Toast.makeText(getContext(), encodepdf, Toast.LENGTH_SHORT).show();

                Log.i("s",encodepdf);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }


    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        cursor.close();

        return fileName;

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

                    if (!s.equals(""))
                    {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialog.setContentView(R.layout.dialog_info_report);
                        dialog.setCancelable(true);

                        ImageView icon = dialog.findViewById(R.id.icon_only);

                        icon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
//                                Intent intent = new Intent(ReportActivity.this, PatientDashboardActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
                            }
                        });

                        dialog.show();

                    }


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
                params.put("report_name", FileName);
                params.put("report", encodepdf);
                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/patient_report", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

    private void uploadVideo() {

        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                uploading = ProgressDialog.show(getContext(), "Uploading your recipe", "Please wait...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                Log.i("sss",s);
//
//                if (s.equals("200"))
//                {
//
//                }
//                else {
//                    Toast.makeText(UploadActivity.this, "Please Upload Your recipe again", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            protected String doInBackground(Void... params) {

                Uploadd u = new Uploadd();
                String ad = u.uploadVideo(PathHolder);
                return ad;

            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

}