package com.limamauricio.bakingapp.utils;

import android.content.Context;

public class SharedPreferencesService {

    private final SharedPreferencesRepository sharedPreferencesRepository;
    private static final String RECIPE_TO_WIDGET = "com.limamauricio.bakingapp.recipe.widget";

    public SharedPreferencesService(Context context){

        this.sharedPreferencesRepository = new SharedPreferencesRepository(context);
    }

    public void storeRecipe(String data){
        sharedPreferencesRepository.store(RECIPE_TO_WIDGET,data);
    }

    public String getStoredData(){
        return sharedPreferencesRepository.getString(RECIPE_TO_WIDGET);
    }



}
