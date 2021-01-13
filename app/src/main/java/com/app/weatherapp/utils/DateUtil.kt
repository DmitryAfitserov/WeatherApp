package com.app.weatherapp.utils

import android.util.Log

class DateUtil {

    var lastTime: Long? = null

    fun isNeedUpdate(timeApi: Long): Boolean{
        if(lastTime == timeApi){

            Log.d("EEE", "lastTime = $lastTime")
            Log.d("EEE", "timeApi = $timeApi")

            return false
        } else {
            lastTime = timeApi
        }
        val timeNow = System.currentTimeMillis()

        val difTime = timeNow - timeApi

        Log.d("EEE", "difTime = $difTime")

      //  return difTime > 1800000 // 30 min
        return difTime > 10000 // 30 min

    }

}