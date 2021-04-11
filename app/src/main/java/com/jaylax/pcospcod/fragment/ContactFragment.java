package com.jaylax.pcospcod.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;

public class ContactFragment extends Fragment  {

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Contact US");

        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

//        mapFragment.getMapAsync(this);

        return view;

    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        LatLng sydney = new LatLng(23.0227968, 72.515584);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("PCOS PCOD CLINIC"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}