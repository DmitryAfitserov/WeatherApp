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
    var liveDataCity: MutableLiveData<String> = MutableLiveData()
    var liveDataWeatherDay: LiveData<WeatherDay> = MutableLiveData()



    fun setCity(city: String){
    }

//    fun checkFirstStart(){
//        val preference= context.getSharedPreferences(
//            context.resources
//                .getString(com.app.weatherapp.R.string.app_name), Context.MODE_PRIVATE
//        )
//        val isFirstStart= preference.getBoolean(KEY_PREF, true)
//        if(isFirstStart){
//            val c = MainCity()
//            c.isEmpty = true
//            GlobalScope.launch {
//                bd.insertMainCity(c)
//            }
//            val weatherDay = WeatherDay()
//            weatherDay.isEmpty = true
////            GlobalScope.launch {
////                bd.insertWeatherDay(weatherDay)
////            }
//            val weatherDays = WeatherSeveralDays()
//            weatherDays.isEmpty = true
////            GlobalScope.launch {
////                bd.insertWeatherSeveralDays(weatherDays)
////            }
//
//            val editor  = preference.edit()
//            editor.putBoolean(KEY_PREF, false)
//            editor.apply()
//        }
//
//    }





    fun getBD(): RepositoryBD{
        return bd
    }

    fun getMainCity() : LiveData<MainCity> {
        liveDataMainCity =  bd.getMainCity()
        return liveDataMainCity
    }

    fun getWeatherDayBD() : LiveData<WeatherDay> {
        liveDataWeatherDay =  bd.getWeatherDay()
        return liveDataWeatherDay
    }



    fun startApp(){

//        val temp = MainCity();
//        temp.mainCity = "Minsk"
//        temp.error = false
//
//
//
//
//        val bd = Repo.getBD(context)
//        GlobalScope.launch {
//            bd.insertMainCity(temp)
//        }






    }

    fun check(){


    }




 //   var liveDataWeatherNow: MutableLiveData<ModelWeatherNow>? = null

//    fun getWeather(): LiveData<ModelWeatherNow>? {
//        liveDataWeatherNow = RepositoryApi.getWeatherNow()
//        return liveDataWeatherNow
//    }


}