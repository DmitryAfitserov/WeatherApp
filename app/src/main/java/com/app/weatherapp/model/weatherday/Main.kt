package com.app.weatherapp.model.weatherday

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Main {



    @SerializedName("temp")
    @Expose
    var temp: Float? = null


    @SerializedName("feels_like")
    @Expose
    var feelsLike: Float? = null


    @SerializedName("temp_min")
    @Expose
    var tempMin: Float? = null


    @SerializedName("temp_max")
    @Expose
    var tempMax: Float? = null

    @SerializedName("pressure")
    @Expose
    var pressure: String? = null

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null

}