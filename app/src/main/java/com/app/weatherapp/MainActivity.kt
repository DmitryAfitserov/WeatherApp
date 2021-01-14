package com.app.weatherapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import androidx.work.*
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.view.AdapterViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(){

    private val tabTitles = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_3,
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

        val requestUpdate = PeriodicWorkRequest.Builder(WorkerUpdate::class.java,
            6, TimeUnit.HOURS, 4, TimeUnit.HOURS).
        setConstraints(constraints).addTag(TAG_WORK).build()

        val requestTest = OneTimeWorkRequest.Builder(WorkerUpdate::class.java).build ()

    //    WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(TAG_WORK, ExistingPeriodicWorkPolicy.KEEP, requestUpdate)

//        WorkManager.getInstance(applicationContext)
//            .getWorkInfoByIdLiveData(requestTest.id)
//            .observe(this, Observer {
//                    workInfo ->
//
//                Log.d("EEE", "workInfo")
//                if (workInfo != null && workInfo.state ==
//                    WorkInfo.State.SUCCEEDED)
//                {
//                    Log.d("EEE", "update")
//                }
//            })

    }

    fun stopWorker(){
        Log.d("MMM", "stopWorker ")
        WorkManager.getInstance(applicationContext).cancelAllWork()
    }

    fun startWorker(time:Long){

        Log.d("MMM", "startWorker " + time.toString())
        val interval = time * 60
        val flexInterval = time * 40

        val requestUpdate = PeriodicWorkRequest.Builder(WorkerUpdate::class.java,
            interval, TimeUnit.MINUTES, flexInterval, TimeUnit.MINUTES).
        setConstraints(constraints).addTag(TAG_WORK).build()
        WorkManager.getInstance(applicationContext).enqueue(requestUpdate)
    }

    fun updateWorker(time:Long){

        Log.d("MMM", "updateWorker " + time.toString())
        val interval = time * 60
        val flexInterval = time * 40

        val requestUpdate = PeriodicWorkRequest.Builder(WorkerUpdate::class.java,
            interval, TimeUnit.MINUTES, flexInterval, TimeUnit.MINUTES).
        setConstraints(constraints).addTag(TAG_WORK).build()
        WorkManager.getInstance(applicationContext).
            enqueueUniquePeriodicWork(TAG_WORK, ExistingPeriodicWorkPolicy.REPLACE, requestUpdate)
    }



//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_options, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.action_settings){
//            Log.d("MMM", "click settings")
//            val fragment = CustomPreferenceFragment()
//            supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
//
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//
//    }


    override fun onDestroy() {
        Log.d("EEE", "destroyBD()")
        Repo.getBD(applicationContext).destroyBD()
        super.onDestroy()

    }
}