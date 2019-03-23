package com.limamauricio.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Step implements Serializable
{

    @SerializedName("id")
    @Getter
    @Setter
    private Integer id;

    @SerializedName("shortDescription")
    @Getter
    @Setter
    private String shortDescription;

    @SerializedName("description")
    @Getter
    @Setter
    private String description;

    @SerializedName("videoURL")
    @Getter
    @Setter
    private String videoURL;

    @SerializedName("thumbnailURL")
    @Getter
    @Setter
    private String thumbnailURL;

}
