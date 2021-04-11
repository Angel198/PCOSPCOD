package com.jaylax.pcospcod.doctorfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.doctoractivities.DoctorDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;

public class DoctorContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_contact, container, false);

        ((DoctorDashboardActivity)getActivity()).setToolbarTitle("About US");

        return view;
    }
}