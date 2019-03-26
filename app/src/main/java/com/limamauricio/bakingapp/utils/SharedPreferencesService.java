package com.limamauricio.bakingapp.utils;

import android.content.Context;

public class SharedPreferencesService {

    private SharedPreferencesRepository sharedPreferencesRepository;
    private static final String RECIPE_TO_WIDGET = "com.limamauricio.bakingapp.recipe.widget";

    public SharedPreferencesService(Context context){

        this.sharedPreferencesRepository = new SharedPreferencesRepository(context);
    }

    public void storeRecipe(String data){
        sharedPreferencesRepository.armazenar(RECIPE_TO_WIDGET,data);
    }

    public String getStoredData(){
        return sharedPreferencesRepository.recuperarString(RECIPE_TO_WIDGET);
    }



}
