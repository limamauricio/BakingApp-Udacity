package com.limamauricio.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Recipe implements Serializable
{

    @SerializedName("id")
    @Getter
    @Setter
    private Integer id;

    @SerializedName("name")
    @Getter
    @Setter
    private String name;

    @SerializedName("ingredients")
    @Getter
    @Setter
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    @Getter
    @Setter
    private List<Step> steps;

    @SerializedName("servings")
    @Getter
    @Setter
    private Integer servings;

    @SerializedName("image")
    @Getter
    @Setter
    private String image;

}
