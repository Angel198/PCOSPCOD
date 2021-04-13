package com.jaylax.pcospcod.doctoractivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaylax.pcospcod.DoctorLoginActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientadapter.ChatAdapter;
import com.jaylax.pcospcod.util.ChatModel;
import java.util.ArrayList;
import java.util.HashMap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorChatActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_id, name, token;
    String patient_id;
    RecyclerView recyclerViewChat;
    ChatAdapter chatAdapter;
    EditText send_msg;
    ImageButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat);

        sharedPreferences = getSharedPreferences(DoctorLoginActivity.MyPREFERENCES_LO, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid",null);
        name = sharedPreferences.getString("nn",null);
        token = sharedPreferences.getString("device_token_fcm",null);

        patient_id = getIntent().getStringExtra("patient_ID");

        send_msg = (EditText) findViewById(R.id.edittxt_chat_Message);
        send = (ImageButton) findViewById(R.id.btnSend);
        recyclerViewChat = (RecyclerView) findViewById(R.id.rv_chat_detail);

        recyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);

        readMessage();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send_msg.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please enter message.",Toast.LENGTH_SHORT).show();
                }else if (patient_id.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Need to select Patient first.",Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(send_msg.getText().toString().trim());
                }
            }

        });

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

                    if ((model.getReceiver().equals(user_id) && model.getSender().equals(patient_id)) || (model.getReceiver().equals(patient_id) && model.getSender().equals(user_id))){
                        chatList.add(model);
                    }
                }

                chatAdapter = new ChatAdapter(user_id,chatList);
                recyclerViewChat.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


    private void sendMessage(String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sender", user_id);
        hashMap.put("receiver", patient_id);
        hashMap.put("message", message);

        reference.child("chat").push().setValue(hashMap);
        send_msg.setText("");

    }

}