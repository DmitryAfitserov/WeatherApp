package com.app.weatherapp.utils

import androidx.room.TypeConverter
import com.app.weatherapp.model.weatherlist.ListData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class WeatherDayConverterRoom {

    @TypeConverter
    fun fromList(list: ArrayList<ListData>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String): ArrayList<ListData> {
        val listType: Type = object : TypeToken<ArrayList<ListData>>() {}.type
        return Gson().fromJson(data, listType)
    }


}