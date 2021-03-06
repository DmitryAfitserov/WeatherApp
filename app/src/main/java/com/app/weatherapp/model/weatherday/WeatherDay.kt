package com.app.weatherapp.model.weatherday

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.weatherapp.utils.WeatherDayConverterRoom
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class WeatherDay {


    @PrimaryKey
    var id: Int = 0

    var timeUpdate: Long? = null

    @SerializedName("list")
    @Expose
    @TypeConverters(WeatherDayConverterRoom::class)
    var list: ArrayList<ListData>? = null

    @Ignore
    var error: String? = null




    var isEmpty: Boolean? = false

}