package com.jaylax.pcospcod.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;

public class ChatFragment extends Fragment {

    EditText send_msg;
    ImageButton send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Chat");

        send_msg = (EditText) view.findViewById(R.id.edittxt_chat_Message);
        send = (ImageButton) view.findViewById(R.id.btnSend);

        return view;

    }

}