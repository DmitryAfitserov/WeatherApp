package com.app.weatherapp.model.weatherforid

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class IdCity {

    @SerializedName("id")
    @Expose
    var id: Int? = null


    var error: String? = null
}