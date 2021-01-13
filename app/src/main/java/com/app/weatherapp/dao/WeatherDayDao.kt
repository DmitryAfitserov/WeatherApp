package com.app.weatherapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.weatherapp.model.weatherday.WeatherDay

@Dao
interface WeatherDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherDay: WeatherDay)

    @Query("SELECT * FROM WeatherDay")
    fun get(): LiveData<WeatherDay>

    @Update
    suspend fun update(weatherDay: WeatherDay)

}