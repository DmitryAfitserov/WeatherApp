package com.app.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.repository.Repo

class FavoriteCitiesViewModel (application: Application) : AndroidViewModel(application) {

    var list: ArrayList<ListData> = arrayListOf()

    private val context = getApplication<Application>().applicationContext
    val bd = Repo.getBD(context)

    fun getWeatherDayBD(): LiveData<WeatherDay> {
        return bd.getWeatherDay()
    }



}