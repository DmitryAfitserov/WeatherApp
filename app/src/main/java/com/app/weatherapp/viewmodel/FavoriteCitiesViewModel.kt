package com.app.weatherapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.MainCity
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherday.Weather
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.repository.Repo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteCitiesViewModel(application: Application) : AndroidViewModel(application) {

    var listToAdapter: ArrayList<ListData> = arrayListOf()

    var liveDataWeatherDay: LiveData<WeatherDay> = MutableLiveData()
    var liveDataMainCity: LiveData<MainCity> = MutableLiveData()

    private var countRequest = 0
    private val referenceRequest = 2

    private val context = getApplication<Application>().applicationContext
    val bd = Repo.getBD(context)


    fun getWeatherDayBD(): LiveData<WeatherDay> {
        liveDataWeatherDay = bd.getWeatherDay()
        return liveDataWeatherDay
    }

    fun getMainCityBD(): LiveData<MainCity> {
        liveDataMainCity = bd.getMainCity()
        return liveDataMainCity
    }

    fun deletePosition(position: Int) {

        if (liveDataWeatherDay.value!!.list!![position].id!!.equals(liveDataMainCity.value!!.mainCityId)) {
            liveDataMainCity.value!!.isFav = false
        } else {
            liveDataWeatherDay.value!!.list!!.removeAt(position)
        }
        insertWeatherDayBD(liveDataWeatherDay.value!!)
        insertMainCityBD(liveDataMainCity.value!!)

    }

    private fun insertWeatherDayBD(weatherDay: WeatherDay) {
        GlobalScope.launch {
            bd.insertWeatherDay(weatherDay)
        }

    }

    private fun insertMainCityBD(mainCity: MainCity) {
        GlobalScope.launch {
            bd.insertMainCity(mainCity)
        }
    }

    fun prepareList() {
        countRequest++
        if (countRequest == referenceRequest) {
            if (liveDataMainCity.value != null && liveDataWeatherDay.value != null) {
                listToAdapter.clear()
                if (liveDataMainCity.value!!.isFav) {
                    Log.d("FFF", "isFav")
                    liveDataWeatherDay.value!!.list?.forEach {
                        listToAdapter.add(it)
                    }
                } else {
                    liveDataWeatherDay.value!!.list?.forEach {
                        if (!liveDataMainCity.value!!.mainCityId!!.equals(it.id))
                            listToAdapter.add(it)
                        Log.d("FFF", "not is fav")
                    }

                }
            }
            countRequest = 0
        }
    }


}