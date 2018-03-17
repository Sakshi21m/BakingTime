package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 13/03/18.
 */

public class ListWidgetService extends RemoteViewsService {

    public static List<ArrayList> ar = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        System.out.println("widget inside ListWidgetService");


        return new WidgetRemoteFactory(this.getApplicationContext());


    }

}

 class WidgetRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

     private Context mContext;

     private int mIngredientCount;
     private List<ArrayList> mIngredientDetails;

     public WidgetRemoteFactory(Context applicationContext){
        mContext =
                applicationContext;

       System.out.println("widget inside WidgetRemoteFactory constructor");

    }
    private void setter(List<ArrayList> list){

        mIngredientDetails= new ArrayList<>();

        if(!list.isEmpty()) {
            mIngredientDetails = list;
            mIngredientCount = list.size();
            System.out.println(" set data is not empty");
        }else{
            mIngredientDetails = null;
            mIngredientCount = 0;
            System.out.println("set data is empty");

        }
    }


    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

        setter(ListWidgetService.ar);
        System.out.println("widget update ic_launcher");



    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        System.out.println("widget inside getCount "+mIngredientCount);
        return mIngredientCount;

    }

    @Override
    public RemoteViews getViewAt(int i) {


         if(mIngredientDetails==null || mIngredientDetails.size()==0){
            System.out.println("widget  list is null");
            return null;}
        else{
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_details);
        views.setTextViewText(R.id.widget_tv_quantity,mIngredientDetails.get(i).get(0).toString() );
        views.setTextViewText(R.id.widget_tv_measure,mIngredientDetails.get(i).get(1).toString() );
        views.setTextViewText(R.id.widget_tv_ingredient_name,mIngredientDetails.get(i).get(2).toString() );
System.out.println("Widget data is set" +mIngredientDetails.get(i).get(2).toString() );

Intent intent = new Intent();
views.setOnClickFillInIntent(R.id.ll_widget_details, intent);
        return views;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        System.out.println("widget inside long getItemId");
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
