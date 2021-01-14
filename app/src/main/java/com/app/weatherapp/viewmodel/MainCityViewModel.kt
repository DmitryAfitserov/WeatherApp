package com.app.weatherapp.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.MainCity
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherforid.IdCity
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.repository.RepositoryBD
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainCityViewModel(application: Application) : AndroidViewModel(application) {

    private val KEY_PREF = "KEYPREF"

    private val context = getApplication<Application>().applicationContext
    val bd = Repo.getBD(context)

    var liveDataMainCity: LiveData<MainCity> = MutableLiveData()
    var liveDataWeatherDay: LiveData<WeatherDay> = MutableLiveData()
    var liveDataWeatherSeveralDays: MutableLiveData<WeatherSeveralDays> = MutableLiveData()
    var liveDataIdCity: MutableLiveData<IdCity> = MutableLiveData()
    var liveDataWeatherMainCity: MutableLiveData<ListData> = MutableLiveData()
     //   var weatherDay: WeatherDay? = null


    fun getMainCity(): LiveData<MainCity> {
        liveDataMainCity = bd.getMainCity()
        return liveDataMainCity
    }

    fun insertMainCity(mainCity: MainCity) {
        GlobalScope.launch {
            bd.insertMainCity(mainCity)
        }
    }

    fun insertWeatherDay(weatherDay: WeatherDay) {
        GlobalScope.launch {
            bd.insertWeatherDay(weatherDay)
        }
    }

    fun getWeatherDayBD(): LiveData<WeatherDay> {
        liveDataWeatherDay = bd.getWeatherDay()
        return liveDataWeatherDay
    }

    fun getWeatherDayAPI(weatherDayCorrect:WeatherDay?): LiveData<WeatherDay> {
        return Repo.getWeatherDay(weatherDayCorrect, liveDataMainCity.value!!.mainCityId)

    }

    fun getWeatherDayApiOb(): LiveData<WeatherDay> {
        return Repo.getWeatherDayOb()
    }

    fun getIdCity(nameCity: String): MutableLiveData<IdCity>{
        liveDataIdCity =  Repo.getIdCityByName(nameCity)
        return liveDataIdCity
    }

    fun getWeatherSeveralDaysApiOB(): MutableLiveData<WeatherSeveralDays>{
        liveDataWeatherSeveralDays = Repo.getWeatherSeveralDaysOb()
        return liveDataWeatherSeveralDays
    }

    fun getWeatherSeveralDaysApi(): MutableLiveData<WeatherSeveralDays>{
        return Repo.getWeatherSeveralDays(liveDataWeatherMainCity.value?.name!!, countDays = 8)
    }

    fun insertWeatherSeveralDays() {
        GlobalScope.launch {
            bd.insertWeatherSeveralDays(liveDataWeatherSeveralDays.value!!)
        }
    }





}