package com.app.weatherapp.model.weatherday

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Wind {

    @SerializedName("speed")
    @Expose
    var speed: String? = null

    @SerializedName("deg")
    @Expose
    var deg: Int? = null


}