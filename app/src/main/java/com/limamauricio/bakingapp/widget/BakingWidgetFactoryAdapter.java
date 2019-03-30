package com.limamauricio.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Ingredient;
import com.limamauricio.bakingapp.model.Recipe;
import com.limamauricio.bakingapp.ui.MainActivity;
import com.limamauricio.bakingapp.utils.SharedPreferencesService;

import java.util.List;

public class BakingWidgetFactoryAdapter implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe recipe;
    private List<Ingredient> ingredientList;
    private SharedPreferencesService sharedPreferencesService;

    public BakingWidgetFactoryAdapter(Context context, Intent intent){

        mContext = context;
        this.sharedPreferencesService = new SharedPreferencesService(context);
    }

    @Override
    public void onCreate() {

        Gson gson = new Gson();
        String recipeData = sharedPreferencesService.getStoredData();
        recipe = gson.fromJson(recipeData,Recipe.class);
        ingredientList = recipe.getIngredients();
    }

    @Override
    public void onDataSetChanged() {

        ingredientList.clear();
        Gson gson = new Gson();
        String recipeData = sharedPreferencesService.getStoredData();
        recipe = gson.fromJson(recipeData,Recipe.class);
        ingredientList = recipe.getIngredients();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_item);
        views.setTextViewText(R.id.txt_ingredient_name,ingredientList.get(position).getIngredient());
        views.setTextViewText(R.id.txt_ingredient_qnt,"( " + ingredientList.get(position).getQuantity()+ " "
                + ingredientList.get(position).getMeasure() + " )" );


        Intent intent = new Intent();
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);
        intent.putExtra(BakingAppWidget.FILTER_RECIPE_ITEM,recipeString);
        views.setOnClickFillInIntent(R.id.container_item,intent);

        return views;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
