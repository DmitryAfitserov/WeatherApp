package com.app.weatherapp.repository

import androidx.lifecycle.MutableLiveData

object Repo {

    fun getIdCityByName(name:String): MutableLiveData<String> {
        return RepositoryApi.getIdCityByName(name)
    }
}