package com.limamauricio.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.limamauricio.bakingapp.model.Recipe;

import java.util.List;

public class Utils {

    public static boolean checkInternetConnection(Context context){

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = cm.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;

    }


    public static boolean checkEmptyList(List<Recipe> recipeList) {

        if (recipeList == null || recipeList.isEmpty())
            return true;
        else
            return false;

    }
}
