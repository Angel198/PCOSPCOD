package com.jaylax.pcospcod.doctorfragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.doctoractivities.CompletedPatientListActivity;
import com.jaylax.pcospcod.doctoractivities.DoctorDashboardActivity;
import com.jaylax.pcospcod.doctoractivities.OngoingPatientListActivity;
import com.jaylax.pcospcod.doctoractivities.PatientInquiryActivity;
import com.jaylax.pcospcod.patientactivities.OngoingTreatmentActivity;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;

public class DoctorHomeFragment extends Fragment {

    LinearLayout layout_one, layout_two, layout_three, layout_four;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_home, container, false);

        ((DoctorDashboardActivity)getActivity()).setToolbarTitle("Home");

        layout_one = (LinearLayout) view.findViewById(R.id.layout_one);
        layout_two = (LinearLayout) view.findViewById(R.id.layout_two);
        layout_three = (LinearLayout) view.findViewById(R.id.layout_three);
        layout_four = (LinearLayout) view.findViewById(R.id.layout_four);

        layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PatientInquiryActivity.class);
                startActivity(intent);
            }
        });


        layout_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), OngoingPatientListActivity.class);
                startActivity(intent);
            }
        });

        layout_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), CompletedPatientListActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }

}