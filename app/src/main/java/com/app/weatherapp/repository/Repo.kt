package com.app.weatherapp.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.weatherforid.IdCity
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.model.weatherseveralday.WeatherSeveralDays

object Repo {

    fun getIdCityByName(name:String): MutableLiveData<IdCity> {
        return RepositoryApi.getIdCityByName(name)
    }

    fun getIdCityByNameOb(): MutableLiveData<IdCity> {
        return RepositoryApi.getLiveDataIdCityOb()
    }

    fun getWeatherDay(weatherDay: WeatherDay?, idCityString: String?) : MutableLiveData<WeatherDay>{
        return RepositoryApi.getWeatherDay(weatherDay, idCityString)
    }

    fun getWeatherDayOb() : MutableLiveData<WeatherDay>{
        return RepositoryApi.getLiveDataWeatherDayOb()
    }

    fun getWeatherSeveralDaysOb() : MutableLiveData<WeatherSeveralDays>{
        return RepositoryApi.getLiveWeatherSeveralDaysOb()
    }

    fun getWeatherSeveralDays(nameCity: String, countDays: Int): MutableLiveData<WeatherSeveralDays> {
        return  RepositoryApi.getWeatherSeveralDays(nameCity, countDays)
    }

    fun getBD(context: Context): RepositoryBD {
        return RepositoryBD(context)
    }

}