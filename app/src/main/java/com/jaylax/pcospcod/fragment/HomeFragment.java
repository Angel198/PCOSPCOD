package com.jaylax.pcospcod.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientHomeActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeEightActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeFiveActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeFoutActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeNineActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeSevenActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeSixActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeTenActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeThreeActivity;
import com.jaylax.pcospcod.patientactivities.PatientHomeTwoActivity;
import com.jaylax.pcospcod.patientactivities.ViewOneActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class HomeFragment extends Fragment {


    TextView one, two, three, four, five, six, seven, eight, nine, ten;
    TextView mark;
    YouTubePlayerView youTubePlayerView;
    LinearLayout view_one;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Home");

        one = (TextView) view.findViewById(R.id.t_one);
        two = (TextView) view.findViewById(R.id.t_two);
        three = (TextView) view.findViewById(R.id.t_three);
        four = (TextView) view.findViewById(R.id.t_four);
        five = (TextView) view.findViewById(R.id.t_five);
        six = (TextView) view.findViewById(R.id.t_six);
        seven = (TextView) view.findViewById(R.id.t_seven);
        eight = (TextView) view.findViewById(R.id.t_eight);
        nine = (TextView) view.findViewById(R.id.t_nine);
        ten = (TextView) view.findViewById(R.id.t_ten);
        mark = (TextView) view.findViewById(R.id.mark);
        youTubePlayerView = view.findViewById(R.id.activity_main_youtubePlayerView);
        view_one = (LinearLayout) view.findViewById(R.id.view_one);


        mark.setSelected(true);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "uviGKkSGoe8";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();
            }

        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PatientHomeActivity.class);
                startActivity(intent);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeTwoActivity.class);
                startActivity(intent);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeThreeActivity.class);
                startActivity(intent);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeFoutActivity.class);
                startActivity(intent);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeFiveActivity.class);
                startActivity(intent);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeSixActivity.class);
                startActivity(intent);
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeSevenActivity.class);
                startActivity(intent);
            }
        });


        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeEightActivity.class);
                startActivity(intent);
            }
        });


        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeNineActivity.class);
                startActivity(intent);
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PatientHomeTenActivity.class);
                startActivity(intent);
            }
        });


        view_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewOneActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }
}