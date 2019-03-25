package com.limamauricio.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Ingredient implements Serializable
{

    @SerializedName("quantity")
    @Getter
    @Setter
    private Double quantity;

    @SerializedName("measure")
    @Getter
    @Setter
    private String measure;

    @SerializedName("ingredient")
    @Getter
    @Setter
    private String ingredient;

}
