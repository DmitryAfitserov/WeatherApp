package com.app.weatherapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.app.weatherapp.model.weatherlist.WeatherDay
import com.app.weatherapp.utils.WeatherDayConverterRoom

@Database(entities = [WeatherDay::class], version = 1)
@TypeConverters(WeatherDayConverterRoom::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherCityDao(): WeatherCityDao


    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "DB").build()
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}