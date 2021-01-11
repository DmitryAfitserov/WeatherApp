package com.app.weatherapp.model.weatherseveralday

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Temp {

    @SerializedName("day")
    @Expose
    var day: Float? = null

    @SerializedName("min")
    @Expose
    var min: Float? = null

    @SerializedName("max")
    @Expose
    var max: Float? = null

    @SerializedName("night")
    @Expose
    var night: Float? = null

    @SerializedName("eve")
    @Expose
    var eve: Float? = null

    @SerializedName("morn")
    @Expose
    var morn: Float? = null

}