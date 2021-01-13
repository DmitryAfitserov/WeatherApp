package com.app.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MainCity {


    @PrimaryKey
    var id: Int = 0


    var mainCity: String? = null

    var isEmpty  = false

}