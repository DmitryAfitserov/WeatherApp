package com.app.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.api.RetrofitBuilder

import com.app.weatherapp.model.weatherforid.IdCity
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Response

import retrofit2.Callback

object RepositoryApi {

    val liveDataIdCity = MutableLiveData<IdCity>()
    val liveDataWeatherDay = MutableLiveData<WeatherDay>()
    val liveDataWeatherSeveralDays = MutableLiveData<WeatherSeveralDays>()


    fun getLiveDataIdCityOb(): MutableLiveData<IdCity>{
        return liveDataIdCity
    }
    fun getLiveDataWeatherDayOb(): MutableLiveData<WeatherDay>{
        return liveDataWeatherDay
    }
    fun getLiveWeatherSeveralDaysOb(): MutableLiveData<WeatherSeveralDays>{
        return liveDataWeatherSeveralDays
    }

    fun getIdCityByName(name: String): MutableLiveData<IdCity> {

        // http://api.openweathermap.org/data/2.5/weather?q=minsk&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val params: MutableMap<String, String> = HashMap()
        params["q"] = name
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"

        val call = RetrofitBuilder.apiInterface.getIdCityByName(params)
        call.enqueue(object : Callback<IdCity> {

            override fun onFailure(call: Call<IdCity>, t: Throwable) {
                //  Log.d("EEE", t.message.toString())
                val idCity = IdCity()
                idCity.error = t.message.toString()
                liveDataIdCity.value = idCity
            }

            override fun onResponse(
                call: Call<IdCity>,
                response: Response<IdCity>
            ) {
                //   Log.d("EEE", response.body().toString())

                var data = response.body()
                if(data == null){
                    var city = IdCity()
                    city.error = "City not found"
                    data = city

                }
                //  Log.d("EEE", "id city  = " + data?.id.toString())
                liveDataIdCity.value = data
            }
        })
        return liveDataIdCity
    }


    fun getWeatherDay(weatherDay: WeatherDay?, idCityString: String?): MutableLiveData<WeatherDay> {

        // http://api.openweathermap.org/data/2.5/group?id=524901,703448,627904&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val paramIds = StringBuilder()

        weatherDay?.let {
            it.list?.forEach {
                paramIds.append(it.id).append(",")
            }
        }
        idCityString?.let {

            if(!paramIds.contains(idCityString)){
                paramIds.append(it)
            }

        }

        val params: MutableMap<String, String> = HashMap()
        params["id"] = paramIds.toString()
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"

        val call = RetrofitBuilder.apiInterface.getWeatherDay(params)

        call.enqueue(object : Callback<WeatherDay> {

            override fun onFailure(call: Call<WeatherDay>, t: Throwable) {
                Log.d("EEE", t.message.toString())
                val weatherDay = WeatherDay()
                weatherDay.error = t.message.toString()
                liveDataWeatherDay.value = weatherDay
            }

            override fun onResponse(
                call: Call<WeatherDay>,
                response: Response<WeatherDay>
            ) {
                Log.d("EEE", response.toString())

                val data = response.body()
                data?.timeUpdate = System.currentTimeMillis()
                liveDataWeatherDay.value = data
            }
        })
        return liveDataWeatherDay
    }

    fun getWeatherSeveralDays(nameCity: String, countDays: Int): MutableLiveData<WeatherSeveralDays> {

        // http://api.openweathermap.org/data/2.5/forecast/daily?q=minsk&cnt=4&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val params: MutableMap<String, String> = HashMap()
        params["q"] = nameCity
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"
        params["cnt"] = countDays.toString()

        val call = RetrofitBuilder.apiInterface.getWeatherSeveralDays(params)
        call.enqueue(object : Callback<WeatherSeveralDays> {

            override fun onFailure(call: Call<WeatherSeveralDays>, t: Throwable) {
                //  Log.d("EEE", t.message.toString())
                val weatherSeveralDays = WeatherSeveralDays()
                weatherSeveralDays.error = t.message.toString()
                liveDataWeatherSeveralDays.value = weatherSeveralDays
            }

            override fun onResponse(
                call: Call<WeatherSeveralDays>,
                response: Response<WeatherSeveralDays>
            ) {
                //   Log.d("EEE", response.body().toString())

                val data = response.body()
                //  Log.d("EEE", "id city  = " + data?.id.toString())
                liveDataWeatherSeveralDays.value = data
            }
        })
        return liveDataWeatherSeveralDays
    }




}