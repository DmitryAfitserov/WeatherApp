package com.app.weatherapp.utils

import android.util.Log

class DateUtil {


    fun isNeedUpdate(timeApi: Long): Boolean{

        val timeNow = System.currentTimeMillis()

        val difTime = timeNow - timeApi



        return difTime > 1800000 // 30 min

    }

}