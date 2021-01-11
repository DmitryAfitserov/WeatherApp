package com.app.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.weatherapp.model.ModelWeatherNow
import com.app.weatherapp.repository.RepositoryApi

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    var liveDataWeatherNow: MutableLiveData<ModelWeatherNow>? = null

//    fun getWeather(): LiveData<ModelWeatherNow>? {
//        liveDataWeatherNow = RepositoryApi.getWeatherNow()
//        return liveDataWeatherNow
//    }


}