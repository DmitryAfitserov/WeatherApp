package com.app.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays
import com.app.weatherapp.repository.Repo

class SeveralDaysViewModel (application: Application) : AndroidViewModel(application) {


    var list: ArrayList<DataWeatherDay> = arrayListOf()

    private val context = getApplication<Application>().applicationContext
    val bd = Repo.getBD(context)

    fun getSeveralDaysBD(): LiveData<WeatherSeveralDays> {
        return bd.getWeatherSeveralDays()
    }
}