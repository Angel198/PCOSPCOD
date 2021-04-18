package com.jaylax.pcospcod.patientactivities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.util.ReportModel;
import com.jaylax.pcospcod.util.RequestHandler;
import com.jaylax.pcospcod.util.Uploadd;
import com.jaylax.pcospcod.util.UriUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReportActivity extends AppCompatActivity {

    TextView upload_file, upload;
    RecyclerView recyclerView;
    String file_name;
    private int REQUEST_CODE = 1;
    String[] mimeTypes;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    String FileName, PdfID, encodepdf;
    String PathHolder;
    private int REQ_PDF = 21;
    ArrayList<ReportModel> reportModels = new ArrayList<>();
    ArrayList re_pdf = new ArrayList();
    RecyclerAdapter recyclerAdapter;
    LinearLayout linearlayout;
    String path;
    Integer pageNumber = 0;
    String pdfFileName;
    TextView f_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        upload_file = (TextView) findViewById(R.id.upload_file);
        upload = (TextView) findViewById(R.id.upload_filee);
        f_name = (TextView) findViewById(R.id.upload_file_name);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearlayout = (LinearLayout) findViewById(R.id.layout);

        initToolbar();

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        editor = sharedPreferences.edit();

        Log.i("user_id",user_id);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent = Intent.createChooser(intent, "Choose a file");
                startActivityForResult(intent, REQ_PDF);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getdata();

                uploadVideo();

            }
        });

        getpatientreport();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PDF && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            FileName = getFileName(data.getData());
            Log.i("FileName",FileName);

            PathHolder = UriUtils.getPathFromUri(this,uri);
            Log.d("Picture Path", PathHolder);

            f_name.setText(PathHolder);

            try {
                InputStream inputStream = ReportActivity.this.getContentResolver().openInputStream(uri);
                byte[] pdfbytes = new byte[inputStream.available()];
                inputStream.read(pdfbytes);
                encodepdf = Base64.encodeToString(pdfbytes, Base64.DEFAULT);

                Toast.makeText(this, encodepdf, Toast.LENGTH_SHORT).show();

                Log.i("s",encodepdf);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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


    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        cursor.close();

        return fileName;

    }



    private void uploadVideo() {

        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading =  new ProgressDialog(ReportActivity.this);
                uploading.setMessage("Uploading.....");
                uploading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                uploading.setCancelable(true);
                uploading.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("sss",s);
                uploading.cancel();
                if (s.equals("200"))
                {
                    getdata();
                }
                else {
                    Toast.makeText(ReportActivity.this, "Please Upload Your report again", Toast.LENGTH_SHORT).show();
                }
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

                    if (!s.equals(""))
                    {
                        final Dialog dialog = new Dialog(ReportActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialog.setContentView(R.layout.dialog_info_report);
                        dialog.setCancelable(true);

                        ImageView icon = dialog.findViewById(R.id.icon_only);

                        icon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
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
//                params.put("report", encodepdf);
                return requestHandler.sendPostRequest("http://curepcos.in/hospitalmanagement/api/patient_report", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

    public void getpatientreport()
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
                        String a = c.getString("patient_id");
                        String b = c.getString("report");
                        String d = c.getString("report_name");

                        String e = "http://curepcos.in/hospitalmanagement/uploads/"+d;
                        re_pdf.add(d);

                        ReportModel reportModel = new ReportModel(a,d,e);
                        reportModels.add(reportModel);
                    }


                    Log.i("size", String.valueOf(re_pdf.size()));

                    recyclerAdapter = new RecyclerAdapter(reportModels,getApplicationContext());
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

                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/get_patient_reports", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private List<ReportModel> mListenerList;
        Context mContext;
        LayoutInflater inflater;

        public RecyclerAdapter(List<ReportModel> mListenerList, Context mContext) {
            this.mListenerList = mListenerList;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            ImageView down_load;
            PDFView pdfView;
            WebView webview;

            public MyViewHolder(View view) {

                super(view);
                name = (TextView) view.findViewById(R.id.r_name);
                down_load = (ImageView) view.findViewById(R.id.t_download);
                webview = (WebView) view.findViewById(R.id.pdfView);

            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_layout, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.name.setText(mListenerList.get(position).getReport_name());
            holder.webview.getSettings().setJavaScriptEnabled(true);
            String url = "https://docs.google.com/viewer?embedded = true&url = "+mListenerList.get(position).getReport();
            holder.webview.loadUrl(url);

        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

    }

}