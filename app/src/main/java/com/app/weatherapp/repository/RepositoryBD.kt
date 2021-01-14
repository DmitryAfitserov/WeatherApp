package com.app.weatherapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.app.weatherapp.dao.AppDatabase
import com.app.weatherapp.dao.MainCityDao
import com.app.weatherapp.dao.WeatherDayDao
import com.app.weatherapp.dao.WeatherSeveralDaysDao
import com.app.weatherapp.model.MainCity
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays


class RepositoryBD(context: Context) {

    private var db = AppDatabase.getAppDataBase(context)
    private var weatherDayDao: WeatherDayDao = db!!.weatherDayDao()
    private var weatherSeveralDaysDao: WeatherSeveralDaysDao = db!!.weatherSeveralDaysDao()
    private var mainCityDao: MainCityDao = db!!.mainCityDao()

    suspend fun insertWeatherDay(weatherDay: WeatherDay) {
        val newId = weatherDayDao.insert(weatherDay)

    }

    fun insertSynchWeatherDay(weatherDay: WeatherDay) {
        val newId = weatherDayDao.insertSynch(weatherDay)
    }

    fun getWeatherDay(): LiveData<WeatherDay> {
        return weatherDayDao.get()
    }

    fun getSynchWeatherDay(): WeatherDay {
        return weatherDayDao.getSynch()
    }



    suspend fun insertWeatherSeveralDays(weatherSeveralDays: WeatherSeveralDays) {
         weatherSeveralDaysDao.insert(weatherSeveralDays)

    }

    fun insertSynchWeatherSeveralDays(weatherSeveralDays: WeatherSeveralDays) {
        weatherSeveralDaysDao.insertSynch(weatherSeveralDays)

    }

    fun getWeatherSeveralDays(): LiveData<WeatherSeveralDays> {
        Log.d("EEE", "getWeatherSeveralDays getWeatherSeveralDays in BD ")
        return weatherSeveralDaysDao.get()
    }

    fun getSynchWeatherSeveralDays(): WeatherSeveralDays {
        Log.d("EEE", "getWeatherSeveralDays getWeatherSeveralDays in BD ")
        return weatherSeveralDaysDao.getSynch()
    }



    suspend fun insertMainCity(mainCity: MainCity): Long? {
        val newId = mainCityDao.insert(mainCity)
        return 0
    }

    fun getMainCity(): LiveData<MainCity> {
        Log.d("EEE", "getMainCity getMainCity in BD ")
        return mainCityDao.get()
    }







    fun destroyBD(){
        AppDatabase.destroyDataBase()
    }

}