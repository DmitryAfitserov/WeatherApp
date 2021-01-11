package com.app.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.ModelWeatherDays
import com.app.weatherapp.model.ModelWeatherNow
import com.app.weatherapp.api.RetrofitBuilder
import com.app.weatherapp.model.weatherforid.IdCity
import retrofit2.Call
import retrofit2.Response

import retrofit2.Callback

object RepositoryApi {

    val liveDataWeatherNow = MutableLiveData<ModelWeatherNow>()

    val liveDataWeatherDays = MutableLiveData<ModelWeatherDays>()

    val liveDataIdCity = MutableLiveData<IdCity>()





    fun getIdCityByName(name: String) : MutableLiveData<IdCity>{

        val params: MutableMap<String, String> = HashMap()
        params["q"] = name
        params["appid"] = "fc199427e9a8ee2bee5dc1222759d908"
        params["units"] = "metric"

        val call = RetrofitBuilder.apiInterface.getIdCityByName(params)

        call.enqueue(object: Callback<IdCity> {

            override fun onFailure(call: Call<IdCity>, t: Throwable) {
                Log.d("EEE", t.message.toString())
                val idCity = IdCity()
                idCity.error = t.message.toString()
                liveDataIdCity.value = idCity

            }

            override fun onResponse( call: Call<IdCity>,
                                     response: Response<IdCity> ) {
                Log.d("EEE", response.body().toString())

                val data = response.body()
                Log.d("EEE", "id city  = " + data?.id.toString())
                liveDataIdCity.value = data
            }
        })

        return liveDataIdCity
    }



}