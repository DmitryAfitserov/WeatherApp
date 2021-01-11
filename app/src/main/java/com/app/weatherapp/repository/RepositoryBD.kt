package com.app.weatherapp.repository

import android.content.Context
import com.app.weatherapp.dao.AppDatabase
import com.app.weatherapp.dao.WeatherCityDao
import com.app.weatherapp.model.weatherlist.ListData
import com.app.weatherapp.model.weatherlist.WeatherDay


class RepositoryBD(context: Context) {

    private var db = AppDatabase.getAppDataBase(context)
    private var weatherCityDao: WeatherCityDao = db!!.weatherCityDao()

    suspend fun insert(weatherDay: WeatherDay): Long? {
        val newId = weatherCityDao.insert(weatherDay)
       // bookmark.id = newId
        return 0
    }

    suspend fun getWeatherDay(): WeatherDay {
        return weatherCityDao.getWeatherDay()
    }

//    fun createBookmark(): Bookmark {
//        return Bookmark()
//    }

//    val allBookmarks: List<ListData?>?
//        get() {
//            return weatherCityDao.getAll()
//        }
}