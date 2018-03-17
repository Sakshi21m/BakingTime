package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingtime.utilities.JsonStepNames;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majmudar on 26/02/18.
 */

public class MasterListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ArrayList>> {

    private String mId=null;
    private String mName;
    private String mJson;
    private TextView ingredient;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private LinearLayout linearLayout;
    private MasterListStepsAdapter mAdapter;
    private final static int BT_Loader = 20;
    private boolean mTwoPane;
    private List<ArrayList> stepDetails;
    private IngredientFragment ingredientFragment;
    private VideoFragment videoFragment;
    private FragmentManager fragmentManager;
    private LinearLayoutManager layoutManager;
private Parcelable layoutManagerSavedState;
    private List<ArrayList>  stepDetailsParcelable;

    private static final String SAVED_LAYOUT_MANAGER="steps";





    public MasterListFragment(){

    }


    public void setters(){
        if(this.getArguments()!=null){
            mId = this.getArguments().getString("id");
            mName = this.getArguments().getString("name");
            mJson = this.getArguments().getString("json");
            System.out.println(" imp json"+mName+mId);
            mTwoPane = this.getArguments().getBoolean("paneMode");

        }else{
            System.out.println("arguments are null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState!=null)
        {mId = savedInstanceState.getString("id");
            mName =savedInstanceState.getString("name");
            mJson = savedInstanceState.getString("json");
            mTwoPane = savedInstanceState.getBoolean("mTwoPane");
            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
            stepDetailsParcelable = (ArrayList) savedInstanceState.getParcelableArrayList("data");


        }
        final View rootView = inflater.inflate(R.layout.master_list_fragment, container, false);
        ingredient = (TextView) rootView.findViewById(R.id.tv_ingredient);
        mErrorMessageDisplay = (TextView) rootView.findViewById(R.id.tv_error_message_display2);
        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator2);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.ll_master_list_fragment);
        fragmentManager = getActivity().getSupportFragmentManager();
        System.out.println(" fragmentManager called");



        ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mId);
                bundle.putString("name", mName);
                bundle.putString("json", mJson);

                if(mTwoPane){
                    System.out.println(" mTwoPane is true");
                    ingredientFragment = new IngredientFragment();
                    ingredientFragment.setArguments(bundle);
                    ingredientFragment.setters();

                    if(fragmentManager.findFragmentByTag("ingredient")!=null){
                        fragmentManager.beginTransaction().replace(R.id.fragment_ingredients_main, ingredientFragment,"ingredient").commit();
                        //replace container if already exist else add it
                        System.out.println(" ingredient container is replaced");

                    }else{
                        fragmentManager.beginTransaction().add(R.id.fragment_ingredients_main, ingredientFragment,"ingredient").commit();
                        System.out.println(" container is added");

                    }
                    if(fragmentManager.findFragmentByTag("videoFragment")!=null) {
                        System.out.println(" videoFragment container is removed");
                        fragmentManager.beginTransaction().remove(videoFragment).commit();
                    }
                }
                else {
                    System.out.println(" mTwoPane is false");

                    Intent intent = new Intent(getContext(), IngredientMain.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_steps_name);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

 if(!mTwoPane) {
     //opens a new activity in phone
     System.out.println(" inside mobile mode ");
     mAdapter = new MasterListStepsAdapter(new MasterListStepsAdapter.OnStepClickListener() {
         @Override
         public void onStepSelected(String stepId) {
             Bundle bundle = new Bundle();

             for (int i = 0; i < stepDetails.size(); i++) {
                 if (stepId.equals(stepDetails.get(i).get(0))) {
                     bundle.putString("stepId", stepDetails.get(i).get(0).toString());
                     bundle.putString("stepName", stepDetails.get(i).get(1).toString());
                     bundle.putString("stepDescription", stepDetails.get(i).get(2).toString());
                     bundle.putString("videoUrl", stepDetails.get(i).get(3).toString());
                     bundle.putString("stepThumbNail", stepDetails.get(i).get(4).toString());
                     bundle.putBoolean("twoPane",mTwoPane);
                     bundle.putString("json",mJson);
                     bundle.putString("recipeId",mId);
                     System.out.println(" video" + stepDetails.get(i).get(3).toString());
                     System.out.println(" video" + stepDetails.get(i).get(1).toString());

                     break;
                 } else {
                     System.out.println(" stepId is" + stepId + "and stepDetails.get(i).get(0) is " + stepDetails.get(i).get(0));
                 }
             }


             Intent intent = new Intent(getContext(), VideoScreenMain.class);
             intent.putExtras(bundle);
             startActivity(intent);
         }
     });
 }else{
     System.out.println(" inside tablet mode ");
     System.out.println(" mTwoPane is true");

     mAdapter = new MasterListStepsAdapter(new MasterListStepsAdapter.OnStepClickListener() {
         @Override
         public void onStepSelected(String stepId) {

             Bundle bundle2 = new Bundle();
             bundle2.putString("id", mId);
             bundle2.putString("name", mName);
             bundle2.putString("json", mJson);
             ingredientFragment = new IngredientFragment();
             ingredientFragment.setArguments(bundle2);
             ingredientFragment.setters();

             if(fragmentManager.findFragmentByTag("ingredient")!=null){
                 fragmentManager.beginTransaction().replace(R.id.fragment_ingredients_main, ingredientFragment,"ingredient").commit();
                 fragmentManager.beginTransaction().remove(ingredientFragment).commit();

                 //replace container if already exist else add it
                 System.out.println(" ingredient container is replaced");

             }

             Bundle bundle = new Bundle();

             for (int i = 0; i < stepDetails.size(); i++) {
                 if (stepId == stepDetails.get(i).get(0)) {
                     bundle.putString("stepId", stepId);
                     bundle.putString("stepName", stepDetails.get(i).get(1).toString());
                     bundle.putString("stepDescription", stepDetails.get(i).get(2).toString());
                     bundle.putString("videoUrl", stepDetails.get(i).get(3).toString());
                     bundle.putString("stepThumbNail", stepDetails.get(i).get(4).toString());
                     bundle.putBoolean("twoPane",mTwoPane);
                     System.out.println(" video" + stepDetails.get(i).get(3).toString());

                     break;
                 } else {
                     System.out.println(" stepId is" + stepId + "and stepDetails.get(i).get(0) is " + stepDetails.get(i).get(0));
                 }
             }
             videoFragment = new VideoFragment();
             videoFragment.setArguments(bundle);
             videoFragment.setter();



             if(fragmentManager.findFragmentByTag("videoFragment")!=null){
                 fragmentManager.beginTransaction().replace(R.id.video_main_container, videoFragment,"videoFragment").commit();
                 System.out.println(" videoFragment container is replaced");


             }else{
                 fragmentManager.beginTransaction().add(R.id.video_main_container, videoFragment,"videoFragment").commit();
                 System.out.println(" videoFragment container is added");

             }

         }
     });

 }
        recyclerView.setAdapter(mAdapter);
        System.out.println("1 loader manager sdf");

        // FragmentActivity fm = new FragmentActivity();
        LoaderManager lm = getLoaderManager();
        Bundle bundle = new Bundle();
        lm.initLoader(BT_Loader,bundle,this);
        System.out.println("1 loader manager initiated");
        return rootView;
    }


    @Override
    public Loader<List<ArrayList>> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<List<ArrayList>>(getActivity()) {

            private List<ArrayList> jSonResult;

    @Override
    public void onStartLoading() {
        super.onStartLoading();
        System.out.println("1 to be set");

        if (args == null) {
            return;
        }
        ingredient.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        forceLoad();
System.out.println("1 views set invisible");
        //caching the Loader


        if(jSonResult!=null){
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);
            ingredient.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            System.out.println(" indicator");

            deliverResult(jSonResult);
        }else{
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);
            ingredient.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            System.out.println(" indicator not set previuosly");
            forceLoad();
        }
    }

    @Override
    public List<ArrayList> loadInBackground() {

        try {
            System.out.println("1 load in bg");

            stepDetails =  JsonStepNames.getSimpleStringsFromJson(mJson,mId);
            return stepDetails;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    //This method is used to make caching of loader
    @Override
    public void deliverResult(List<ArrayList> ls){
        jSonResult = ls;
        super.deliverResult(ls);
    }
};
    }

    @Override
    public void onLoadFinished(Loader<List<ArrayList>> loader, List<ArrayList> data) {

        List<ArrayList> pmDataSet;
        pmDataSet = data;
        linearLayout.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        ingredient.setVisibility(View.VISIBLE);
        if (pmDataSet != null) {
            System.out.println("2 pmdataset is "+pmDataSet.size());
            mAdapter.setData(pmDataSet);
        } else {
            System.out.println("2 pmdataset is null ");
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ArrayList>> loader) {
        System.out.println("2 onLoaderReset");

    }
    private void showErrorMessage() {
        ingredient.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        System.out.println("inside onSaveInstanceState");
        layoutManagerSavedState = layoutManager.onSaveInstanceState();

        state.putString("json",mJson);
        state.putString("name",mName);
        state.putString("id",mId);
        state.putBoolean("mTwoPane",mTwoPane);
        state.putParcelable(SAVED_LAYOUT_MANAGER,layoutManagerSavedState );
        state.putParcelableArrayList("data",(ArrayList)stepDetails);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (layoutManagerSavedState != null) {
            layoutManager.onRestoreInstanceState(layoutManagerSavedState);
        }
        stepDetails = stepDetailsParcelable;

    }





}
