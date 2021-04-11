package com.jaylax.pcospcod.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;
import com.jaylax.pcospcod.util.VideoModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class TestimonialFragment extends Fragment {

    private YouTubePlayerView youTubePlayerView, youTubePlayerView1, youTubePlayerView2, youTubePlayerView3, youTubePlayerView4, youTubePlayerView5,
            youTubePlayerView6, youTubePlayerView7, youTubePlayerView8, youTubePlayerView9, youTubePlayerView10;
    GridView coursesGV;
    ArrayList<VideoModel> videoModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testimonial, container, false);

        ((PatientDashboardActivity)getActivity()).setToolbarTitle("Testimonial");

//        coursesGV = view.findViewById(R.id.idGVcourses);
//
//        videoModels.add(new VideoModel("VjRmznh2Zl0"));
//        videoModels.add(new VideoModel("iNvbfqJSLA4"));
//        videoModels.add(new VideoModel("0GbSCWsWKPs"));
//        videoModels.add(new VideoModel("E3_ePOLgGDI"));
//        videoModels.add(new VideoModel("921TKSqai74"));
//        videoModels.add(new VideoModel("xBc6uObj_Wg"));
//        videoModels.add(new VideoModel("q4M-TRb41ps"));
//        videoModels.add(new VideoModel("noTdtf-tEBA"));
//        videoModels.add(new VideoModel("ne4o8V_Bt3w"));
//        videoModels.add(new VideoModel("laAT94fM2Bw"));
//        videoModels.add(new VideoModel("NVYzGTmH9BA"));
//        videoModels.add(new VideoModel("N5YY1YAzTNc"));
//        videoModels.add(new VideoModel("I64V8xWKZIc"));
//        videoModels.add(new VideoModel("E1mY_60YQmU"));
//        videoModels.add(new VideoModel("3GHglEotsZE"));
//        videoModels.add(new VideoModel("ki89xgbgz7g"));
//
//        VideoAdapter videoAdapter = new VideoAdapter(getContext(), videoModels);
//        coursesGV.setAdapter(videoAdapter);



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

    public class VideoAdapter extends ArrayAdapter<VideoModel> {

        public VideoAdapter(@NonNull Context context, ArrayList<VideoModel> courseModelArrayList) {
            super(context, 0, courseModelArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listitemView = convertView;
            if (listitemView == null) {
                // Layout Inflater inflates each item to be displayed in GridView.
                listitemView = LayoutInflater.from(getContext()).inflate(R.layout.testimonial_layout, parent, false);
            }

            VideoModel videoModel = getItem(position);
            YouTubePlayerView youTubePlayerView = listitemView.findViewById(R.id.activity_main_youtubePlayerView);

            getLifecycle().addObserver(youTubePlayerView);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                    String videoId = "VjRmznh2Zl0";
                    youTubePlayer.loadVideo(videoModel.getId(), 0);


                }

            });


            return listitemView;
        }

    }

}