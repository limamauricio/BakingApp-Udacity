package com.limamauricio.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.ui.MainActivity;
import com.limamauricio.bakingapp.ui.RecipeDetailsActivity;
import com.limamauricio.bakingapp.utils.SharedPreferencesService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    private static final String FILTER_RECIPE = "FILTER_RECIPE";
    public static final String FILTER_RECIPE_ITEM = "FILTER_RECIPE_ITEM";
    private static final String OPEN_MAIN_ACTIVITY = "OPEN_MAIN_ACTIVITY";
    private SharedPreferencesService sharedPreferencesService;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        Intent intent = new Intent(context,BakingAppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setRemoteAdapter(R.id.widget_ingredients,intent);
        views.setTextViewText(R.id.appwidget_text,context.getResources().getString(R.string.app_name));
        views.setEmptyView(R.id.widget_ingredients,R.id.txt_no_recipe_found);

        Intent iMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,iMainActivity,0);
        iMainActivity.setAction(OPEN_MAIN_ACTIVITY);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

        Intent iRecipeDetails = new Intent(context, BakingAppWidget.class);
        iRecipeDetails.setAction(FILTER_RECIPE);
        PendingIntent pendingIntentRecipeDetails = PendingIntent.getBroadcast(context,0,iRecipeDetails,0);
        views.setPendingIntentTemplate(R.id.widget_ingredients,pendingIntentRecipeDetails);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null){

            if (intent.getAction().equalsIgnoreCase(FILTER_RECIPE)){

                String recipeString = intent.getStringExtra(FILTER_RECIPE_ITEM);

                Intent i = new Intent(context, RecipeDetailsActivity.class);
                i.putExtra(FILTER_RECIPE_ITEM,recipeString);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }else if (intent.getAction().equalsIgnoreCase(OPEN_MAIN_ACTIVITY)){

                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);

            }

        }

        super.onReceive(context, intent);

    }
}

