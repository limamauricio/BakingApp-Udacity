package com.limamauricio.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepository {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesRepository(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void armazenar(String key, String valor) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(key, valor);
        sharedPreferencesEditor.apply();
    }

    public String recuperarString(String key) {
        return sharedPreferences.getString(key, null);
    }

}
