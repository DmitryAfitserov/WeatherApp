package com.app.weatherapp.model.weatherseveralday

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.weatherapp.utils.CityConverterRoom
import com.app.weatherapp.utils.WeatherDayConverterRoom
import com.app.weatherapp.utils.WeatherSeveralDaysConverterRoom
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity
class WeatherSeveralDays {

    @PrimaryKey
    var id: Int = 0

    @TypeConverters(CityConverterRoom::class)
    @SerializedName("city")
    @Expose
    var city: City? = null

    @SerializedName("cod")
    @Expose
    var cod: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null

    @TypeConverters(WeatherSeveralDaysConverterRoom::class)
    @SerializedName("list")
    @Expose
    var list: ArrayList<DataWeatherDay>? = null

    @Ignore
    var error:String? = null

    var isEmpty: Boolean? = false

}