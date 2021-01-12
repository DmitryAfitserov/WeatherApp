package com.app.weatherapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.app.weatherapp.dao.AppDatabase
import com.app.weatherapp.dao.WeatherCityDao
import com.app.weatherapp.model.weatherday.WeatherDay


class RepositoryBD(context: Context) {

    private var db = AppDatabase.getAppDataBase(context)
    private var weatherCityDao: WeatherCityDao = db!!.weatherCityDao()

    suspend fun insertWeatherDay(weatherDay: WeatherDay): Long? {
        val newId = weatherCityDao.insert(weatherDay)
        // bookmark.id = newId
        return 0
    }

    fun getWeatherDay(): LiveData<WeatherDay> {
        Log.d("EEE", "getWeatherDay in BD ")
        return weatherCityDao.getWeatherDay()
    }

    suspend fun updateWeatherDay(weatherDay: WeatherDay): Long? {
        val newId = weatherCityDao.updateWeatherDay(weatherDay)
        // bookmark.id = newId
        return 0
    }

}