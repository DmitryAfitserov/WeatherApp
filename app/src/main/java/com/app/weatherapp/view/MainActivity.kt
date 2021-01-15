package com.app.weatherapp.view


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.app.weatherapp.R
import com.app.weatherapp.WorkerUpdate
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.view.adapters.AdapterViewPager
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(){

    private val tabTitles = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
    )
    private val TAG_WORK = "TAGG"
    private lateinit var constraints: Constraints

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewPager.adapter = AdapterViewPager(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = applicationContext.resources.getString(tabTitles[position])

            viewPager.setCurrentItem(tab.position, true)
        }.attach()


        constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()


    }

    fun stopWorker(){
        WorkManager.getInstance(applicationContext).cancelAllWork()
    }

    fun startWorker(time:Long){
        val interval = time * 60
        val flexInterval = time * 40
        val requestUpdate = PeriodicWorkRequest.Builder(
            WorkerUpdate::class.java,
            interval, TimeUnit.MINUTES, flexInterval, TimeUnit.MINUTES).
        setConstraints(constraints).addTag(TAG_WORK).build()
        WorkManager.getInstance(applicationContext).enqueue(requestUpdate)
    }

    fun updateWorker(time:Long){
        val interval = time * 60
        val flexInterval = time * 40
        val requestUpdate = PeriodicWorkRequest.Builder(
            WorkerUpdate::class.java,
            interval, TimeUnit.MINUTES, flexInterval, TimeUnit.MINUTES).
        setConstraints(constraints).addTag(TAG_WORK).build()
        WorkManager.getInstance(applicationContext).
            enqueueUniquePeriodicWork(TAG_WORK, ExistingPeriodicWorkPolicy.REPLACE, requestUpdate)
    }


    override fun onDestroy() {
        Log.d("EEE", "destroyBD()")
        Repo.getBD(applicationContext).destroyBD()
        super.onDestroy()

    }
}