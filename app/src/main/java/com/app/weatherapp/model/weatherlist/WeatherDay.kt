package com.app.weatherapp.model.weatherlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherDay {
    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null

    @SerializedName("list")
    @Expose
    var list: List<ListData>? = null


    var error: String? = null


}