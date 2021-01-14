package com.app.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays
import com.app.weatherapp.repository.Repo

class SeveralDaysViewModel (application: Application) : AndroidViewModel(application) {


    var list: ArrayList<DataWeatherDay> = arrayListOf()
    var livaDataSeveralDays: LiveData<WeatherSeveralDays> = MutableLiveData()

    private val context = getApplication<Application>().applicationContext
    val bd = Repo.getBD(context)

    fun getSeveralDaysBD(): LiveData<WeatherSeveralDays> {
        livaDataSeveralDays = bd.getWeatherSeveralDays()
        return livaDataSeveralDays
    }

    fun prepareList(weatherSeveralDays: WeatherSeveralDays){
        list.clear()
        weatherSeveralDays.list?.forEach {
            list.add(it)
        }

    }
}