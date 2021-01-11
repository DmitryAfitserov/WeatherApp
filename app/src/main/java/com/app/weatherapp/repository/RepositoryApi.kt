package com.app.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.ModelWeatherDays
import com.app.weatherapp.model.ModelWeatherNow
import com.app.weatherapp.api.RetrofitBuilder

import com.app.weatherapp.model.weatherforid.IdCity
import com.app.weatherapp.model.weatherlist.WeatherDay
import retrofit2.Call
import retrofit2.Response

import retrofit2.Callback

object RepositoryApi {

 //   val liveDataWeatherNow = MutableLiveData<ModelWeatherNow>()

    val liveDataWeatherDay = MutableLiveData<WeatherDay>()

    val liveDataIdCity = MutableLiveData<IdCity>()



    fun getIdCityByName(name: String) : MutableLiveData<IdCity>{

        // http://api.openweathermap.org/data/2.5/weather?q=minsk&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val params: MutableMap<String, String> = HashMap()
        params["q"] = name
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"

        val call = RetrofitBuilder.apiInterface.getIdCityByName(params)

        call.enqueue(object: Callback<IdCity> {

            override fun onFailure(call: Call<IdCity>, t: Throwable) {
              //  Log.d("EEE", t.message.toString())
                val idCity = IdCity()
                idCity.error = t.message.toString()
                liveDataIdCity.value = idCity

            }

            override fun onResponse( call: Call<IdCity>,
                                     response: Response<IdCity> ) {
             //   Log.d("EEE", response.body().toString())

                val data = response.body()
              //  Log.d("EEE", "id city  = " + data?.id.toString())
                liveDataIdCity.value = data
            }
        })

        return liveDataIdCity
    }


    fun getWeatherDay(weatherDay: WeatherDay?, idCityString: String?) : MutableLiveData<WeatherDay>{

       // http://api.openweathermap.org/data/2.5/group?id=524901,703448,627904&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val paramIds = StringBuilder()

        weatherDay?.let {
            it.list?.forEach {
                paramIds.append(it.id).append(",")
            }
        }
        idCityString?.let {
            paramIds.append(it)
        }

        Log.d("EEE", "paramIds  = " + paramIds.toString())



        val params: MutableMap<String, String> = HashMap()
        params["id"] = paramIds.toString()
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"

        val call = RetrofitBuilder.apiInterface.getWeatherDay(params)

        call.enqueue(object: Callback<WeatherDay> {

            override fun onFailure(call: Call<WeatherDay>, t: Throwable) {
                Log.d("EEE", t.message.toString())
                val weatherDay = WeatherDay()
                weatherDay.error = t.message.toString()
                liveDataWeatherDay.value = weatherDay
            }

            override fun onResponse( call: Call<WeatherDay>,
                                     response: Response<WeatherDay> ) {
                Log.d("EEE", response.toString())

                val data = response.body()
                liveDataWeatherDay.value = data
            }
        })

        return liveDataWeatherDay
    }


}