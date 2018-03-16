package com.example.android.bakingtime;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingtime.Utilities.NetworkUtils;
import com.example.android.bakingtime.Utilities.jsonAllRecipeName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterAllRecipeNames.RecipeAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<ArrayList>>{

    private RecyclerView recyclerView;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private final static int BT_LOADER = 10;
    private AdapterAllRecipeNames mAdapter;
    private String jsonPMResponse="abc";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_all_recipe);

        GridLayoutManager gridLayoutManager;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(this,1);
        }else{
            gridLayoutManager = new GridLayoutManager(this,2);

        }
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new AdapterAllRecipeNames(this);
        recyclerView.setAdapter(mAdapter);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        Bundle bundle2 = new Bundle();
        //sharedPreferences = this.getSharedPreferences("com.example.android.", Context.MODE_PRIVATE);

        LoaderManager lm = getSupportLoaderManager();
        lm.initLoader(BT_LOADER,bundle2,this);

    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }



    @Override
    public Loader<List<ArrayList>> onCreateLoader(int id, final Bundle args) {
            System.out.println("sakshi this"+this.toString());

        return new AsyncTaskLoader<List<ArrayList>>(this) {
            private List<ArrayList> jSonResult;

            @Override
            public void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }
                recyclerView.setVisibility(View.INVISIBLE);
                mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();

                //caching the Loader


                if(jSonResult!=null){
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    System.out.println("sakshi indicator");

                    deliverResult(jSonResult);
                }else{
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    System.out.println("sakshi indicator not set previuosly");
                    forceLoad();
                }
}

            @Override
            public List<ArrayList> loadInBackground() {

                URL popularMovieURL = NetworkUtils.popularMovieBuildUrl();

                        try {
                            System.out.println("sakshi 111 jsonPMResponse"+jsonPMResponse.length());

                            jsonPMResponse = NetworkUtils
                                    .getResponseFromHttpUrl(popularMovieURL);
                            return jsonAllRecipeName
                                    .getSimplePMStringsFromJson(jsonPMResponse);

                        } catch (Exception e) {
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
        recyclerView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        if (pmDataSet != null) {
            mAdapter.setPMData(pmDataSet);
        } else {
            showErrorMessage();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<ArrayList>> loader) {

    }

    @Override
    public void onClick(List recipeDetails) {

        try {

             /*Fetching the data from ArrayList with key value pair
                * key   value
                * 0     movie id
                * 1     vote average
                * 2     title
                * 3     poster path
                * 4     plot synopsis
                * 5     release date
                * */
            String id = recipeDetails.get(0).toString();
            String name =recipeDetails.get(1).toString();
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("name", name);
            bundle.putString("json",jsonPMResponse);
            System.out.println("sakshi imp jsonPMResponselenght"+jsonPMResponse.length());
            Intent intent = new Intent(this, MasterListMain.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }catch(Exception e ){
            e.printStackTrace();
        }



    }


    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        System.out.println("inside onSaveInstanceState");
        state.putString("json",jsonPMResponse);
    }


    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        System.out.println("inside onRestoreInstanceState");

        if (state !=null) {
            jsonPMResponse = state.getString("json");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("inside onResume");

    }
}
