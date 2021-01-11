package com.app.weatherapp.repository

import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.model.weatherforid.IdCity

object Repo {

    fun getIdCityByName(name:String): MutableLiveData<IdCity> {
        return RepositoryApi.getIdCityByName(name)
    }
}