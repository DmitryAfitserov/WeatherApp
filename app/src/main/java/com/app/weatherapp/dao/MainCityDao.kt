package com.app.weatherapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.weatherapp.model.MainCity

@Dao
interface MainCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mainCity: MainCity)

    @Query("SELECT * FROM MainCity")
    fun get(): LiveData<MainCity>

    @Update
    suspend fun update(mainCity: MainCity)

}