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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SeveralDaysViewModel(application: Application) : AndroidViewModel(application) {


    var list: ArrayList<DataWeatherDay> = arrayListOf()
    var livaDataSeveralDays: MutableLiveData<WeatherSeveralDays> = MutableLiveData()

    private val context = getApplication<Application>().applicationContext
    val bd = Repo.getBD(context)

    fun getSeveralDaysBD(): LiveData<WeatherSeveralDays> {
        return bd.getWeatherSeveralDays()
    }

    fun prepareList(weatherSeveralDays: WeatherSeveralDays) {

        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")


        list.clear()
        weatherSeveralDays.list?.forEach {

            val dateString = formatter.format(Date(((it.dt!!.toLong()) * 1000)))

            it.dt = dateString
            list.add(it)

        }

    }
}