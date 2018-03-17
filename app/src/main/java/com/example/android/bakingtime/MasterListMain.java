package com.example.android.bakingtime;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by majmudar on 25/02/18.
 */

public class MasterListMain extends AppCompatActivity implements MasterListStepsAdapter.OnStepClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_list_fragment_main);

        boolean mTwoPane;


        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String jsonPMResponse = getIntent().getStringExtra("json");
        setTitle(name);

        if(savedInstanceState==null) {

            if (findViewById(R.id.two_pane_mode) != null) {
                System.out.println("two pane mode is set true");
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("name", name);
                bundle.putString("json", jsonPMResponse);
                bundle.putBoolean("paneMode", true);
                bundle.putBoolean("twoPane", true);


                MasterListFragment masterListFragment = new MasterListFragment();
                masterListFragment.setArguments(bundle);
                masterListFragment.setters();
                System.out.println("MasterListFrament is set");

                IngredientFragment ingredientFragment = new IngredientFragment();
                ingredientFragment.setArguments(bundle);
                ingredientFragment.setters();

                VideoFragment videoFragment = new VideoFragment();
                videoFragment.setArguments(bundle);
                videoFragment.setter();


                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.master_list_fragment, masterListFragment).commit();

                if (fragmentManager.findFragmentByTag("videoFragment") == null)
                    fragmentManager.beginTransaction().add(R.id.fragment_ingredients_main, ingredientFragment,"ingredient").commit();


            } else {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("name", name);
                bundle.putString("json", jsonPMResponse);
                bundle.putBoolean("paneMode", false);

                MasterListFragment masterListFragment = new MasterListFragment();
                masterListFragment.setArguments(bundle);
                masterListFragment.setters();
                System.out.println("MasterListFrament is set");
                getSupportFragmentManager().beginTransaction().add(R.id.master_list_fragment, masterListFragment,"ingredient").commit();

            }
        }


    }


    @Override
    public void onStepSelected(String stepId) {
        System.out.println(" stepid" + stepId);
    }
}
