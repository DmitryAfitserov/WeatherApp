package com.app.weatherapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.app.weatherapp.repository.Repo
import java.util.concurrent.TimeUnit

class WorkerUpdate(private var context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    val bd = Repo.getBD(context)

    override fun doWork(): Result {



        val weatherDay = bd.getSynchWeatherDay()

        val weatherDayNew = Repo.getSynchWeatherDay(weatherDay)
        if(weatherDayNew.error != null){
            return Result.failure()
        }

        val severalDays = bd.getSynchWeatherSeveralDays()


        val severalDaysNew = Repo.getSynchWeatherSeveralDays(severalDays.city?.name!!, severalDays.list!!.size)
        if(severalDaysNew.error != null){
            return Result.failure()
        }

        bd.insertSynchWeatherDay(weatherDayNew)
        bd.insertSynchWeatherSeveralDays(severalDaysNew)

        return Result.success()
    }
}