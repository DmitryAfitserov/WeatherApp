package com.app.weatherapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.weatherapp.model.MainCity

@Dao
interface MainCityDao {

    @Insert
    suspend fun insert(mainCity: MainCity)

    @Query("SELECT * FROM MainCity")
    fun get(): LiveData<MainCity>

    @Update
    suspend fun update(mainCity: MainCity)

}