package com.app.weatherapp.model.weatherseveralday

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class WeatherSeveralDays {

    @SerializedName("city")
    @Expose
    var city: City? = null

    @SerializedName("cod")
    @Expose
    var cod: String? = null

    @SerializedName("message")
    @Expose
    var message: Float? = null

    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null

    @SerializedName("list")
    @Expose
    var list: List<DataWeatherDay>? = null

    var error:String? = null

}