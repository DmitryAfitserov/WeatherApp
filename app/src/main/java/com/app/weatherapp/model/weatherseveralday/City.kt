package com.app.weatherapp.model.weatherseveralday

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class City {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
//
//    @SerializedName("coord")
//    @Expose
//    var coord: Coord? = null

//    @SerializedName("country")
//    @Expose
//    var country: String? = null

//    @SerializedName("population")
//    @Expose
//    var population: String? = null
//
//    @SerializedName("timezone")
//    @Expose
//    var timezone: String? = null

}