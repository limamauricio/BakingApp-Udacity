package com.limamauricio.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepository {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesRepository(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void store(String key, String valor) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(key, valor);
        sharedPreferencesEditor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

}
