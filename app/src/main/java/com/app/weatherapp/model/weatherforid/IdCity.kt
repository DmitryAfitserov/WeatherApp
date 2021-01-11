package com.app.weatherapp.model.weatherforid

import com.app.weatherapp.model.weatherlist.Clouds
import com.app.weatherapp.model.weatherlist.Sys
import com.app.weatherapp.model.weatherlist.Weather
import com.app.weatherapp.model.weatherlist.Wind
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class IdCity {

    @SerializedName("id")
    @Expose
    var id: Int? = null


    var error: String? = null
}