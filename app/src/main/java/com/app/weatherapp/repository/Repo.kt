package com.app.weatherapp.repository

import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.weatherforid.IdCity
import com.app.weatherapp.model.weatherlist.WeatherDay

object Repo {

    fun getIdCityByName(name:String): MutableLiveData<IdCity> {
        return RepositoryApi.getIdCityByName(name)
    }

    fun getWeatherDay(weatherDay: WeatherDay?, idCityString: String?) : MutableLiveData<WeatherDay>{
        return RepositoryApi.getWeatherDay(weatherDay, idCityString)
    }
}