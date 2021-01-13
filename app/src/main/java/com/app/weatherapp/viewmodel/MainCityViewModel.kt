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
 //   var liveDataWeatherDay: LiveData<WeatherDay> = MutableLiveData()
        var weatherDay: WeatherDay? = null


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
       // liveDataWeatherDay =
        return bd.getWeatherDay()
    }

    fun getWeatherDayAPI(): LiveData<WeatherDay> {
        return Repo.getWeatherDay(weatherDay, liveDataMainCity.value!!.mainCityId)

    }

    fun getIdCity(nameCity: String): MutableLiveData<IdCity>{
        return Repo.getIdCityByName(nameCity)
    }





    //   var liveDataWeatherNow: MutableLiveData<ModelWeatherNow>? = null

//    fun getWeather(): LiveData<ModelWeatherNow>? {
//        liveDataWeatherNow = RepositoryApi.getWeatherNow()
//        return liveDataWeatherNow
//    }


}