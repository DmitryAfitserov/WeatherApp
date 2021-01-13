package com.app.weatherapp.utils

import androidx.room.TypeConverter
import com.app.weatherapp.model.weatherseveralday.City
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CityConverterRoom {

    @TypeConverter
    fun fromObject(city: City): String? {
        val gson = Gson()
        return gson.toJson(city)
    }

    @TypeConverter
    fun toObject(data: String?): City {
        val objectType: Type = object : TypeToken<City>() {}.type
        return Gson().fromJson(data, objectType)
    }


}