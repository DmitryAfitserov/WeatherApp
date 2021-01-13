package com.app.weatherapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays

@Dao
interface WeatherSeveralDaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherSeveralDays: WeatherSeveralDays)

    @Query("SELECT * FROM WeatherSeveralDays")
    fun get(): LiveData<WeatherSeveralDays>

    @Update
    suspend fun update(weatherSeveralDays: WeatherSeveralDays)
}