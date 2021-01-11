package com.app.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.ModelWeatherDays
import com.app.weatherapp.model.ModelWeatherNow
import com.app.weatherapp.api.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

import retrofit2.Callback

object Repository {

    val liveDataWeatherNow = MutableLiveData<ModelWeatherNow>()

    val liveDataWeatherDays = MutableLiveData<ModelWeatherDays>()

    fun getWeatherNow() : MutableLiveData<ModelWeatherNow>{
        upDateWeatherNow()
        return liveDataWeatherNow
    }

    fun upDateWeatherNow(){

        // http://api.openweathermap.org/data/2.5/weather?q=minsk&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val params: MutableMap<String, String> = HashMap()
        params["q"] = "minsk"
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"

        val call = RetrofitBuilder.apiInterface.getWeatherNow(params)

        call.enqueue(object: Callback<ModelWeatherNow> {

            override fun onFailure(call: Call<ModelWeatherNow>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse( call: Call<ModelWeatherNow>,
                                     response: Response<ModelWeatherNow> ) {
                Log.v("EEE", response.body().toString())

                val data = response.body()

                liveDataWeatherNow.value = data
            }
        })

    }

    fun getWeatherDays(count: String) : MutableLiveData<ModelWeatherDays>{
        upDateWeatherDays(count)
        return liveDataWeatherDays
    }

    fun upDateWeatherDays(count: String){

        // http://api.openweathermap.org/data/2.5/forecast/daily?q=minsk&cnt=4&appid=fc199427e9a8ee2bee5dc1222759d908&units=metric

        val params: MutableMap<String, String> = HashMap()
        params["q"] = "minsk"
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"
        params["cnt"] = count

        val call = RetrofitBuilder.apiInterface.getWeatherDays(params)
        call.enqueue(object: Callback<ModelWeatherDays> {

            override fun onFailure(call: Call<ModelWeatherDays>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse( call: Call<ModelWeatherDays>,
                                     response: Response<ModelWeatherDays> ) {
                Log.v("EEE", response.body().toString())

                val data = response.body()

                liveDataWeatherDays.value = data
            }
        })


    }

}