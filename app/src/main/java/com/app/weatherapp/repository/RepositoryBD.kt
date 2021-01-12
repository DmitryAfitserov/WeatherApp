package com.app.weatherapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.app.weatherapp.dao.AppDatabase
import com.app.weatherapp.dao.WeatherDayDao
import com.app.weatherapp.dao.WeatherSeveralDaysDao
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays


class RepositoryBD(context: Context) {

    private var db = AppDatabase.getAppDataBase(context)
    private var weatherDayDao: WeatherDayDao = db!!.weatherDayDao()
    private var weatherSeveralDaysDao: WeatherSeveralDaysDao = db!!.weatherSeveralDaysDao()

    suspend fun insertWeatherDay(weatherDay: WeatherDay): Long? {
        val newId = weatherDayDao.insert(weatherDay)
        // bookmark.id = newId
        return 0
    }

    fun getWeatherDay(): LiveData<WeatherDay> {
        Log.d("EEE", "getWeatherDay in BD ")
        return weatherDayDao.get()
    }

    suspend fun updateWeatherDay(weatherDay: WeatherDay): Long? {
        val newId = weatherDayDao.update(weatherDay)
        // bookmark.id = newId
        return 0
    }

    suspend fun insertWeatherSeveralDays(weatherSeveralDays: WeatherSeveralDays): Long? {
        val newId = weatherSeveralDaysDao.insert(weatherSeveralDays)
        // bookmark.id = newId
        return 0
    }

    fun getWeatherSeveralDays(): LiveData<WeatherSeveralDays> {
        Log.d("EEE", "getWeatherDay in BD ")
        return weatherSeveralDaysDao.get()
    }

    suspend fun updateWeatherSeveralDays(weatherSeveralDays: WeatherSeveralDays): Long? {
        val newId = weatherSeveralDaysDao.update(weatherSeveralDays)
        // bookmark.id = newId
        return 0
    }

    fun destroyBD(){
        AppDatabase.destroyDataBase()
    }

}