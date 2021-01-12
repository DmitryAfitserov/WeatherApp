package com.app.weatherapp.model.weatherseveralday

import com.app.weatherapp.model.weatherday.Weather
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataWeatherDay {

    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null

    @SerializedName("temp")
    @Expose
    var temp: Temp? = null

    @SerializedName("feels_like")
    @Expose
    var feelsLike: FeelsLike? = null

    @SerializedName("pressure")
    @Expose
    var pressure: String? = null

    @SerializedName("humidity")
    @Expose
    var humidity: String? = null

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @SerializedName("speed")
    @Expose
    var speed: String? = null

    @SerializedName("deg")
    @Expose
    var deg: Int? = null

    @SerializedName("clouds")
    @Expose
    var clouds: String? = null

    @SerializedName("pop")
    @Expose
    var pop: String? = null

    @SerializedName("snow")
    @Expose
    var snow: Float? = null

}