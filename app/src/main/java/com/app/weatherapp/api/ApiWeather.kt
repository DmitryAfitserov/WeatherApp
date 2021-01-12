package com.app.weatherapp.api

import com.app.weatherapp.model.weatherforid.IdCity
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays
import retrofit2.Call
import retrofit2.http.*


interface ApiWeather {


    @GET("data/2.5/weather")
    fun getIdCityByName(@QueryMap params: Map<String, String>): Call<IdCity>

    @GET("data/2.5/group")
    fun getWeatherDay(@QueryMap params: Map<String, String>): Call<WeatherDay>

    @GET("data/2.5/forecast/daily")
    fun getWeatherSeveralDays(@QueryMap params: Map<String, String>): Call<WeatherSeveralDays>
}