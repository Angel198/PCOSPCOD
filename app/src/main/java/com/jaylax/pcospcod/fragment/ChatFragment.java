package com.jaylax.pcospcod.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaylax.pcospcod.LoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.patientadapter.ChatAdapter;
import com.jaylax.pcospcod.util.ChatModel;
import com.jaylax.pcospcod.util.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {

    EditText send_msg;
    ImageButton send;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token, doctor_id = "";
    RecyclerView recyclerViewChat;
    ChatAdapter chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ((PatientDashboardActivity) getActivity()).setToolbarTitle("Chat");

        send_msg = (EditText) view.findViewById(R.id.edittxt_chat_Message_Patient);
        send = (ImageButton) view.findViewById(R.id.btnSendPatient);
        recyclerViewChat = (RecyclerView) view.findViewById(R.id.rv_chat_detail_patient);

        recyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);


        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid", null);
        name = sharedPreferences.getString("nn", null);
        token = sharedPreferences.getString("device_token_fcm", null);
        editor = sharedPreferences.edit();

        Log.i("user_id", user_id);


        getdata();
        
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send_msg.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(),"Please enter message.",Toast.LENGTH_SHORT).show();
                }else if (doctor_id.isEmpty()){
                    Toast.makeText(getActivity(),"Need to select Doctor first.",Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(send_msg.getText().toString().trim());
                }
            }
        });

        return view;


    }

    public void getdata() {

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

                    for (int i = 0; i < jsonArray.length(); ++i) {

                        JSONObject c = jsonArray.getJSONObject(i);

                        String a = c.getString("name");
                        doctor_id = c.getString("id");
                        String d = c.getString("specialization");
                        String e = c.getString("hospital_address");
                        String f = c.getString("email");
                        String g = c.getString("mobile_number");
                        String h = c.getString("description");
                        String j = c.getString("profile_image_url");


                    }

                    readMessage();

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

                return requestHandler.sendPostRequest("http://pcospcod.curepcos.in/api/get_patient_doctor", params);

            }

        }

        UserLogin ul = new UserLogin();
        ul.execute();

    }

    private void sendMessage(String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sender", user_id);
        hashMap.put("receiver", doctor_id);
        hashMap.put("message", message);

        reference.child("chat").push().setValue(hashMap);
        send_msg.setText("");
    }

    private void readMessage() {
        ArrayList<ChatModel> chatList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatModel model = dataSnapshot.getValue(ChatModel.class);

                    if ((model.getReceiver().equals(user_id) && model.getSender().equals(doctor_id)) || (model.getReceiver().equals(doctor_id) && model.getSender().equals(user_id))) {
                        chatList.add(model);
                    }
                }

                chatAdapter = new ChatAdapter(user_id, chatList);
                recyclerViewChat.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}