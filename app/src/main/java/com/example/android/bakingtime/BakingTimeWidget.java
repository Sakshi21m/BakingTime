package com.example.android.bakingtime;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidget extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Bundle bundle,String recipeName,
                                int appWidgetId) {


        System.out.println("widget inside baking time widget");

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
       /* if (width<300)
        //{
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);
            views.setTextViewText(R.id.appwidget_text, "Ingredients for "+recipeName);
            Intent intent = new Intent(context,IngredientMain.class);
            intent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

            System.out.println("widget inside min width");

        //}else{*/

            System.out.println("widget inside big width");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
            System.out.println("widget after R.layout.widget_list_view");
            Intent intent = new Intent(context, ListWidgetService.class);
            System.out.println("widget after ListWidgetService");

            views.setRemoteAdapter(R.id.widget_list_view, intent);
            System.out.println("widget after 1");

            // Set the PlantDetailActivity intent to launch when clicked
            Intent intent2 = new Intent(context,IngredientMain.class);
            System.out.println("widget after 2");

            intent2.putExtras(bundle);
            System.out.println("widget after 3");

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            System.out.println("widget after 4 ");

            views.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);
            System.out.println("widget after 5");

            // Handle empty gardens
            views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            System.out.println("widget after 6");


        //}
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Bundle bundle = new Bundle();
        bundle.putString("id", "1");
        bundle.putString("name", "");
        bundle.putString("json", "abcd");
        String name="";
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,bundle,name, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                               Bundle bundle,String recipeName, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,bundle,recipeName, appWidgetId);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

