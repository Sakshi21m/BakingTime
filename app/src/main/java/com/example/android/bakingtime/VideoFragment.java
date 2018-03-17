package com.example.android.bakingtime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import com.example.android.bakingtime.utilities.JsonStepNames;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by  on 10/03/18.
 */

public class VideoFragment extends Fragment {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private String stepId ;
    private String name;
    private String stepDescription ;
    private String videoUrl;
    private Boolean twoPane;
    private String json;
    private String recipeId;
    private List<ArrayList> stepDetails;
    private long seekToPosition=0;
    String stepThumbNail;

    public VideoFragment() {

    }

    public void setter(){
        if(this.getArguments()!=null){
            stepId=this.getArguments().getString("stepId");
            name=this.getArguments().getString("stepName");
            stepDescription=this.getArguments().getString("stepDescription");
            videoUrl=this.getArguments().getString("videoUrl");
            twoPane = this.getArguments().getBoolean("twoPane");
            json = this.getArguments().getString("json");
            recipeId = this.getArguments().getString("recipeId");
            stepThumbNail = this.getArguments().getString("stepThumbNail");
            System.out.println(" video link is "+videoUrl);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState!=null)
        {System.out.println("Inside savedInstanceState not null");
            stepId = savedInstanceState.getString("stepId");
            name =savedInstanceState.getString("stepName");
            stepDescription = savedInstanceState.getString("stepDescription");
            twoPane = savedInstanceState.getBoolean("twoPane");
            videoUrl = savedInstanceState.getString("videoUrl");
            json= savedInstanceState.getString("json");
            recipeId =savedInstanceState.getString("recipeId");
              stepDetails = (ArrayList) savedInstanceState.getParcelableArrayList("data");
            seekToPosition = savedInstanceState.getLong("exoState");
            stepThumbNail = savedInstanceState.getString("stepThumbNail");



        }

        System.out.println(" inside create");

        final View rootView = inflater.inflate(R.layout.video_details, container, false);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
        final TextView description = (TextView) rootView.findViewById(R.id.tv_step_description);
        Button next = (Button)rootView.findViewById(R.id.button_next_step);
        Button previous = (Button)rootView.findViewById(R.id.button_previous_step);
        ImageView stepThumbNailImage = (ImageView)rootView.findViewById(R.id.thumb_nail);


        if(videoUrl!=null && !videoUrl.isEmpty()) {
            if (!twoPane) {

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    hideSystemUi();
                //video takes up full screen in Landscape mode phone
            }
           // initializePlayer();
            System.out.println(" player initialised");

        }else{
            mPlayerView.setVisibility(View.GONE);
            System.out.println(" set to invisible");

        }

        description.setText(stepDescription);
        if(stepThumbNail.equals(""))
        {

            stepThumbNailImage.setVisibility(View.GONE);

        }else
        {
            Uri link = Uri.parse(stepThumbNail);
            Picasso.with(getContext()).load(link).into(stepThumbNailImage);
            System.out.println("stepThumbNail!=null"+stepThumbNail);

        }

        if(twoPane){
            next.setVisibility(View.GONE);
            previous.setVisibility(View.GONE);
        }else{

            String urlNext=null;
            String descriptionNext="";
            String newStepId="";

            String urlPrevious="";
            String descriptionPrevious="";
            String newStepIdPrevious="";
            stepDetails = new ArrayList<>();
            try {
                stepDetails = JsonStepNames.getSimpleStringsFromJson(json,recipeId);


                for (int i = 0; i < stepDetails.size(); i++) {

                    if (stepId.equals(stepDetails.get(0).get(0).toString())){
                        previous.setVisibility(View.GONE);
                        System.out.println(" this is first step");

                    }
                    if (stepId.equals(stepDetails.get(i).get(0).toString())) {

                        if(i==(stepDetails.size()-1))
                        {
                            next.setVisibility(View.GONE);
                            System.out.println(" this is last step");

                        }else
                        {
                            urlNext = stepDetails.get(i + 1).get(3).toString();
                            descriptionNext = stepDetails.get(i + 1).get(2).toString();
                            newStepId = stepDetails.get(i + 1).get(0).toString();
                            System.out.println(" inside loop for urlNext " + urlNext);
                            if(i>0) {
                                urlPrevious = stepDetails.get(i - 1).get(3).toString();
                                descriptionPrevious = stepDetails.get(i - 1).get(2).toString();
                                newStepIdPrevious = stepDetails.get(i - 1).get(0).toString();
                            }
                        }

                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String desc = descriptionNext;
            final String url =urlNext ;
            final String newStepId2 = newStepId;

            final String prevDesc = descriptionPrevious;
            final String prevUrl =urlPrevious ;
            final String prevStepId = newStepIdPrevious;

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("stepId", newStepId2);
                    bundle.putString("stepName", "");
                    bundle.putString("stepDescription", desc);
                    System.out.println(" inside loop for stepDescription next"+desc);
                    bundle.putString("videoUrl", url);
                    bundle.putString("stepThumbNail", "");
                    bundle.putBoolean("twoPane",twoPane);
                    bundle.putString("json",json);
                    bundle.putString("recipeId",recipeId);
                    System.out.println(" new data for next set");

                    Intent intent = new Intent(getContext(), VideoScreenMain.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    System.out.println("started new act");

                }
            });

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("stepId", prevStepId);
                    bundle.putString("stepName", "");
                    bundle.putString("stepDescription", prevDesc);
                    System.out.println(" inside loop for prevDesc"+prevDesc);
                    bundle.putString("videoUrl", prevUrl);
                    bundle.putString("stepThumbNail", "");
                    bundle.putBoolean("twoPane",twoPane);
                    bundle.putString("json",json);
                    bundle.putString("recipeId",recipeId);
                    System.out.println(" new data for next set");

                    Intent intent = new Intent(getContext(), VideoScreenMain.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    System.out.println("started new act");
                }
            });
        }

        System.out.println(" trying to return root view");
        return rootView;
    }

    private void releasePlayer() {

        if(!videoUrl.isEmpty()) {

            if (mExoPlayer != null) {
                mExoPlayer.stop();
                mExoPlayer.release();

            }

            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        System.out.println(" inside resume");
        if(videoUrl!=null && !videoUrl.isEmpty()) {
            if (!twoPane) {
                hideSystemUi();
                //video takes up full screen in phone
            }

        if ((Util.SDK_INT <= 23 || mExoPlayer == null)){


                initializePlayer();
                System.out.println(" initialise player");
            }

        }

    }

    public void initializePlayer() {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            Uri mediaUri = Uri.parse(videoUrl);
            String userAgent = Util.getUserAgent(getActivity(), "Bakingtime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (seekToPosition!=0){
                mExoPlayer.seekTo(seekToPosition);
            }
            mExoPlayer.setPlayWhenReady(mExoPlayer.getPlayWhenReady());


        }else{
            System.out.println("exoplayer is not null");

        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        System.out.println("inside onSaveInstanceState");

        state.putString("stepId",stepId);
        state.putString("stepName",name);
        state.putString("stepDescription",stepDescription);
        state.putString("videoUrl",videoUrl);
        state.putBoolean("twoPane",twoPane);
        state.putString("json",json);
        state.putString("recipeId",recipeId );
        state.putParcelableArrayList("data",(ArrayList)stepDetails);
        if(mExoPlayer!=null)
        state.putLong("exoState",mExoPlayer.getCurrentPosition());
        else{
            state.putLong("exoState",0);

        }
        state.putString("stepThumbNail", stepThumbNail);

    }


}