package com.app.weatherapp.model.weatherday

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Main {



    @SerializedName("temp")
    @Expose
    var temp: String? = null


    @SerializedName("feels_like")
    @Expose
    var feelsLike: String? = null


    @SerializedName("temp_min")
    @Expose
    var tempMin: String? = null


    @SerializedName("temp_max")
    @Expose
    var tempMax: String? = null

//    @SerializedName("pressure")
//    @Expose
//    var pressure: String? = null

    @SerializedName("humidity")
    @Expose
    var humidity: String? = null

}