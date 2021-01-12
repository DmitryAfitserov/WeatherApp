package com.app.weatherapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherday.Main
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.utils.WeatherDayConverterRoom
import com.app.weatherapp.view.SectionsPagerAdapter

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




//test 4
        val temp = WeatherDay();
        temp.list = arrayListOf<ListData>()


        val temp1 = ListData()
        temp1.name = "name2"

        val temp2 = ListData()
        temp2.name = "name2"
        temp2.dt = 5
        val main = Main()
        main.pressure = "300"
        temp2.main = main

        temp.list?.add(temp1)
        temp.list?.add(temp2)


        val converter = WeatherDayConverterRoom()

        Log.d("EEE", "list start size " + temp.list!!.size.toString())
        Log.d("EEE", "list start size " + temp.list!!.size.toString())

        val string = converter.fromList(temp.list!!)

        Log.d("EEE", "list contein " + string)

        val list = converter.toList(string!!)

        Log.d("EEE", "list size " + list.size)


        val bd = Repo.getBD(applicationContext)
//        GlobalScope.launch {
//            bd.insertWeatherDay(temp)
//        }

//                GlobalScope.launch {
//            bd.updateWeatherDay(temp)
//        }


    //    var weatherDay:  LiveData<WeatherDay>? = bd.getWeatherDay()

        bd.getWeatherDay().observe(this, { weather ->
            weather?.let {
                Log.d("EEE", "list name " + weather.id)
                Log.d("EEE", "list name " + weather.list!![0].name)
                Log.d("EEE", "list name main pressure" + weather.list!![1].main?.pressure)
            }

        })

//        GlobalScope.launch {
//          //  weatherDay = bd.getWeatherDay()
//        }






        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

        }


    }
}