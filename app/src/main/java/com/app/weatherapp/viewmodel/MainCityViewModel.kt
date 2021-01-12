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
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.repository.RepositoryBD
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
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


    fun checkFirstStart(){
        val preference= context.getSharedPreferences(
            context.resources
                .getString(com.app.weatherapp.R.string.app_name), Context.MODE_PRIVATE
        )
        val isFirstStart= preference.getBoolean(KEY_PREF, true)
        if(isFirstStart){
            val c = MainCity()
            c.error = true
            GlobalScope.launch {
                bd.insertMainCity(c)
            }
            val editor  = preference.edit()
            editor.putBoolean(KEY_PREF, false)
            editor.apply()
        }

    }

    fun checkLocation(){

//        val intent = Intent("android.location.GPS_ENABLED_CHANGE")
//        intent.putExtra("enabled", true)
//        context.sendBroadcast(intent)

        var addresses: List<Address>
        val geocoder = Geocoder(context, Locale.getDefault())


        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                for (location in locationResult.locations) {
                    if (location != null) {
                        addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val address: String = addresses[0].getAddressLine(0)
                        val city: String = addresses[0].locality

                        Log.d("EEE", "city $city")
                    }
                }
            }
        }


        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
//            .addOnSuccessListener { location: Location? ->
//
//                if(location == null){
//                    Log.d("EEE", "location null")
//                } else {
//                    addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                    val address: String = addresses[0].getAddressLine(0)
//                    val city: String = addresses[0].locality
//
//                    Log.d("EEE", "city $city")
//                }
//
//            }

    }




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