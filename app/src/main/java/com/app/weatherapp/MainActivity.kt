package com.app.weatherapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.app.weatherapp.model.weatherlist.ListData
import com.app.weatherapp.model.weatherlist.Main
import com.app.weatherapp.model.weatherlist.WeatherDay
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.repository.RepositoryBD
import com.app.weatherapp.utils.WeatherDayConverterRoom
import com.app.weatherapp.view.SectionsPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


//        var id = Repo.getIdCityByName("Minsk")
//     //   Log.d("EEE", "id city in  Main Activity" + id.value)
//        id.observe(this, {idCity ->
//
//            idCity.error?.let {
//                Log.d("EEE", "error  " + idCity.error)
//            } ?: run{
//                Log.d("EEE", "id city in  Main Activity  " + idCity.id)
//            }
//
//        })



//        var weatherDay = WeatherDay()
//        val list = arrayListOf<ListData>()
//
//        val listData = ListData()
//        listData.id = "524901"
//
//        val listData1 = ListData()
//        listData1.id = "703448"
//
//        val listData2 = ListData()
//        listData2.id = "627904"
//
//        list.add(listData)
//        list.add(listData1)
//        list.add(listData2)
//        weatherDay.list = list
//
//        var weather = Repo.getWeatherDay(weatherDay, "624079")
//        //   Log.d("EEE", "id city in  Main Activity" + id.value)
//        weather.observe(this, {wd ->
//
//            wd.error?.let {
//                Log.d("EEE", "error  " + it)
//            } ?: run{
//
//                Log.d("EEE", "name city in  Main Activity  "
//                        + wd.list!![2].id
//                        + wd.list!!.size
//
//                    // + wd.list.toString()
//                )
//
//                Log.d("EEE", "name city in  Main Activity  "
//                        + wd.list!![3].id
//                        + wd.list!!.size
//
//                       // + wd.list.toString()
//                )
//            }
//
//        })



//        var weather = Repo.getWeatherSeveralDays("Minsk", 4)
//        //   Log.d("EEE", "id city in  Main Activity" + id.value)
//        weather.observe(this, {wd ->
//
//            wd.error?.let {
//                Log.d("EEE", "error  " + it)
//            } ?: run{
//
//                Log.d("EEE", "name city in  Main Activity  "
//                        + wd.list!![2].pressure
//                       // + wd.list!!.size
//
//                    // + wd.list.toString()
//                )
//
//                Log.d("EEE", "name city in  Main Activity  "
//                        + wd.list!![3].pressure
//                        + wd.list!!.size
//
//                       // + wd.list.toString()
//                )
//            }
//
//        })



        val temp = WeatherDay();
        temp.list = arrayListOf<ListData>()


        val temp1 = ListData()
        temp1.name = "name2"

        val temp2 = ListData()
        temp2.name = "name2"

        temp.list?.add(temp1)
        temp.list?.add(temp2)


        val converter = WeatherDayConverterRoom()

        Log.d("EEE", "list start size " + temp.list!!.size.toString())

        val string = converter.fromList(temp.list!!)

        Log.d("EEE", "list contein " + string)

        val list = converter.toList(string!!)

        Log.d("EEE", "list size " + list!!.size)


        val bd = RepositoryBD(applicationContext)
//        GlobalScope.launch {
//            bd.insert(temp)
//        }


        var weatherDay:WeatherDay? = null

        GlobalScope.launch {
            weatherDay = bd.getWeatherDay()
        }




     //   val list =  bd.allBookmarks

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            if(weatherDay == null){
           //     Log.d("EEE", "list sosi")
            }
            Log.d("EEE", "list name " + weatherDay?.id)
            Log.d("EEE", "list name " + weatherDay?.list!![0].name)

        }


    }
}