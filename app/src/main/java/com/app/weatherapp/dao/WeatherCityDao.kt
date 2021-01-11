package com.app.weatherapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.weatherapp.model.weatherlist.WeatherDay

@Dao
interface WeatherCityDao {

    @Insert
    suspend fun insert(weatherDay: WeatherDay)

    @Query("SELECT * FROM WeatherDay")
    suspend fun getWeatherDay(): WeatherDay

}