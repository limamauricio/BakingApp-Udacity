package com.limamauricio.bakingapp.proxy;

import com.limamauricio.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Proxy {

    @GET("baking.json")
    Call<List<Recipe>> getAll();

}
