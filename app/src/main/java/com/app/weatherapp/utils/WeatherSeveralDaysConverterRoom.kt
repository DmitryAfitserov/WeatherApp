package com.app.weatherapp.utils

import androidx.room.TypeConverter
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherSeveralDaysConverterRoom {

    @TypeConverter
    fun fromList(list: ArrayList<DataWeatherDay>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String): ArrayList<DataWeatherDay> {
        val listType: Type = object : TypeToken<ArrayList<DataWeatherDay>>() {}.type
        return Gson().fromJson(data, listType)
    }

}