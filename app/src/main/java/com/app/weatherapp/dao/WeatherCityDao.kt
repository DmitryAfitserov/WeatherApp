package com.app.weatherapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.weatherapp.model.weatherday.WeatherDay

@Dao
interface WeatherCityDao {

    @Insert
    suspend fun insert(weatherDay: WeatherDay)

    @Query("SELECT * FROM WeatherDay")
    fun getWeatherDay(): LiveData<WeatherDay>

    @Update
    suspend fun updateWeatherDay(weatherDay: WeatherDay)

}