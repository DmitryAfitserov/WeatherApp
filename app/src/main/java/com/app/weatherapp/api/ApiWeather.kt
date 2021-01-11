package com.app.weatherapp.api

import com.app.weatherapp.model.ModelWeatherDays
import com.app.weatherapp.model.ModelWeatherNow
import com.app.weatherapp.model.weatherforid.IdCity
import retrofit2.Call
import retrofit2.http.*


interface ApiWeather {

    @GET("data/2.5/weather")
    fun getWeatherNow(@QueryMap params: Map<String, String>): Call<ModelWeatherNow>

    @GET("data/2.5/weather")
    fun getIdCityByName(@QueryMap params: Map<String, String>): Call<IdCity>

    @GET("data/2.5/forecast/daily")
    fun getWeatherDays(@QueryMap params: Map<String, String>): Call<ModelWeatherDays>
}