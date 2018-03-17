package com.example.android.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.android.bakingtime.utilities.JsonIngredientDetails;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majmudar on 27/02/18.
 */

public class IngredientFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ArrayList>> {


    private String mRecipeId;
    private String mJson;
    private String mName;
    private TextView title;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private LinearLayout linearLayout;
    private IngredientAdapter mAdapter;
    private final static int BT_Loader = 30;


    public IngredientFragment(){

}

    public void setters(){
        if(this.getArguments()!=null){
            mRecipeId = this.getArguments().getString("id");
            mJson = this.getArguments().getString("json");
            mName = this.getArguments().getString("name");

            System.out.println("setters called ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.ingredients_recycler, container, false);
        title = (TextView) rootView.findViewById(R.id.tv_title_ingredient);
        mErrorMessageDisplay = (TextView) rootView.findViewById(R.id.tv_error_message_display3);
        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator3);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.ll_ingredient_fragment);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_ingredients);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
             mAdapter = new IngredientAdapter();
            recyclerView.setAdapter(mAdapter);
        LoaderManager lm = getLoaderManager();
        Bundle bundle = new Bundle();
        lm.initLoader(BT_Loader,bundle,this);
        // return rootView;
        return rootView;
    }

    @Override
    public Loader<List<ArrayList>> onCreateLoader(int id, Bundle args) {


        return new AsyncTaskLoader<List<ArrayList>>(getContext()) {
            private List<ArrayList> jSonResult;


            @Override
            public void onStartLoading(){
                super.onStartLoading();

                title.setVisibility(View.INVISIBLE);
                mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                mLoadingIndicator.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                forceLoad();


                if(jSonResult!=null){
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                    title.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    deliverResult(jSonResult);
                }else{
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                    title.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }


            @Override
            public List<ArrayList> loadInBackground() {
                try {
                    return JsonIngredientDetails.getSimpleStringsFromJson(mJson,mRecipeId);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

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
        title.setVisibility(View.VISIBLE);
        if (pmDataSet != null) {
            System.out.println(" Number of ingredients are  "+pmDataSet.size());
            mAdapter.setter(pmDataSet);
        } else {
            System.out.println(" ingredients data set is null ");
            showErrorMessage();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), BakingTimeWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_list_view);

        new ListWidgetService();
        System.out.println("widget listWidgetService called");
        ListWidgetService.ar = pmDataSet;
        //Now update all widgets

        Bundle bundle2 = new Bundle();
        bundle2.putString("id", mRecipeId);
        bundle2.putString("name", mName);
        bundle2.putString("json", mJson);
        BakingTimeWidget.updateIngredientWidgets(getActivity(), appWidgetManager, bundle2,mName,appWidgetIds);

    }

    @Override
    public void onLoaderReset(Loader<List<ArrayList>> loader) {

    }
    private void showErrorMessage() {
        title.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }
}
