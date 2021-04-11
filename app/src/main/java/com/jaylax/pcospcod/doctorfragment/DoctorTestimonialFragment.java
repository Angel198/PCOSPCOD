package com.jaylax.pcospcod.doctorfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.doctoractivities.DoctorDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class DoctorTestimonialFragment extends Fragment {

    private YouTubePlayerView youTubePlayerView, youTubePlayerView1, youTubePlayerView2, youTubePlayerView3, youTubePlayerView4, youTubePlayerView5,
            youTubePlayerView6, youTubePlayerView7, youTubePlayerView8, youTubePlayerView9, youTubePlayerView10;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_testimonial, container, false);

        ((DoctorDashboardActivity)getActivity()).setToolbarTitle("Testimonial");


        youTubePlayerView = view.findViewById(R.id.activity_main_youtubePlayerView);
        youTubePlayerView1 = view.findViewById(R.id.activity_main_youtubePlayerView1);
        youTubePlayerView2 = view.findViewById(R.id.activity_main_youtubePlayerView2);
        youTubePlayerView3 = view.findViewById(R.id.activity_main_youtubePlayerView3);
        youTubePlayerView4 = view.findViewById(R.id.activity_main_youtubePlayerView4);
        youTubePlayerView5 = view.findViewById(R.id.activity_main_youtubePlayerView5);
        youTubePlayerView6 = view.findViewById(R.id.activity_main_youtubePlayerView6);
        youTubePlayerView7 = view.findViewById(R.id.activity_main_youtubePlayerView7);
        youTubePlayerView8 = view.findViewById(R.id.activity_main_youtubePlayerView8);
        youTubePlayerView9 = view.findViewById(R.id.activity_main_youtubePlayerView9);
        youTubePlayerView10 = view.findViewById(R.id.activity_main_youtubePlayerView10);

        getLifecycle().addObserver(youTubePlayerView);
        getLifecycle().addObserver(youTubePlayerView1);
        getLifecycle().addObserver(youTubePlayerView2);
        getLifecycle().addObserver(youTubePlayerView3);
        getLifecycle().addObserver(youTubePlayerView4);
        getLifecycle().addObserver(youTubePlayerView5);
        getLifecycle().addObserver(youTubePlayerView6);
        getLifecycle().addObserver(youTubePlayerView7);
        getLifecycle().addObserver(youTubePlayerView8);
        getLifecycle().addObserver(youTubePlayerView9);
        getLifecycle().addObserver(youTubePlayerView10);


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "VjRmznh2Zl0";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();
            }

        });

        youTubePlayerView1.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "iNvbfqJSLA4";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();

            }

        });

        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "0GbSCWsWKPs";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();

            }

        });

        youTubePlayerView3.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "E3_ePOLgGDI";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        youTubePlayerView4.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "921TKSqai74";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });
        youTubePlayerView5.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "xBc6uObj_Wg";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        youTubePlayerView6.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "q4M-TRb41ps";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        youTubePlayerView7.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "noTdtf-tEBA";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        youTubePlayerView8.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "laAT94fM2Bw";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        youTubePlayerView9.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "NVYzGTmH9BA";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        youTubePlayerView10.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "N5YY1YAzTNc";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();


            }

        });

        return view;
    }
}