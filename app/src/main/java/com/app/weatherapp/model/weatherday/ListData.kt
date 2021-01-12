package com.app.weatherapp.model.weatherday


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ListData {
//    @SerializedName("coord")
//    @Expose
//    var coord: Coord? = null


    @SerializedName("sys")
    @Expose
    var sys: Sys? = null


    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null


    @SerializedName("main")
    @Expose
    var main: Main? = null


    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null


    @SerializedName("wind")
    @Expose
    var wind: Wind? = null


    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null


    @SerializedName("dt")
    @Expose
    var dt: Int? = null


    @SerializedName("id")
    @Expose
    var id: String? = null


    @SerializedName("name")
    @Expose
    var name: String? = null
}