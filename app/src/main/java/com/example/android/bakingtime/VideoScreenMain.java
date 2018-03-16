package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sakshimajmudar on 10/03/18.
 */

public class VideoScreenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main_container);

        System.out.println("videoScreenMain");

        if(savedInstanceState==null) {


            Intent intent = getIntent();

            String stepId = intent.getStringExtra("stepId");
            String name = getIntent().getStringExtra("stepName");
            String stepDescription = getIntent().getStringExtra("stepDescription");
            String stepUrl = getIntent().getStringExtra("videoUrl");
            String stepThumbNail = getIntent().getStringExtra("stepThumbNail");
            Boolean twoPane = getIntent().getBooleanExtra("twoPane", false);
            String json = getIntent().getStringExtra("json");
            String recipeId = getIntent().getStringExtra("recipeId");
            System.out.println("sakshi video name" + name + "url" + stepUrl);

            Bundle bundle = new Bundle();
            bundle.putString("stepId", stepId);
            bundle.putString("name", name);
            bundle.putString("stepDescription", stepDescription);
            bundle.putString("videoUrl", stepUrl);
            bundle.putString("stepThumbNail", stepThumbNail);
            bundle.putBoolean("twoPane", twoPane);
            bundle.putString("json", json);
            bundle.putString("recipeId", recipeId);


            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            videoFragment.setter();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.video_main_container, videoFragment, "videoFragment").commit();
        }else{

            System.out.println("inside savedInstanceState!=null");
            Intent intent = getIntent();

            String stepId = intent.getStringExtra("stepId");
            String name = getIntent().getStringExtra("stepName");
            String stepDescription = getIntent().getStringExtra("stepDescription");
            String stepUrl = getIntent().getStringExtra("videoUrl");
            String stepThumbNail = getIntent().getStringExtra("stepThumbNail");
            Boolean twoPane = getIntent().getBooleanExtra("twoPane", false);
            String json = getIntent().getStringExtra("json");
            String recipeId = getIntent().getStringExtra("recipeId");
            System.out.println("sakshi video name" + name + "url" + stepUrl);

            Bundle bundle = new Bundle();
            bundle.putString("stepId", stepId);
            bundle.putString("name", name);
            bundle.putString("stepDescription", stepDescription);
            bundle.putString("videoUrl", stepUrl);
            bundle.putString("stepThumbNail", stepThumbNail);
            bundle.putBoolean("twoPane", twoPane);
            bundle.putString("json", json);
            bundle.putString("recipeId", recipeId);


            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            videoFragment.setter();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.video_main_container, videoFragment, "videoFragment").commit();
        }
    }
    }
