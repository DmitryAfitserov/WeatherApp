package com.app.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay
import com.app.weatherapp.viewmodel.FavoriteCitiesViewModel
import com.app.weatherapp.viewmodel.SeveralDaysViewModel

class WeatherSeveralDaysFragment: Fragment() {

    private lateinit var severalDaysViewModel: SeveralDaysViewModel
    private lateinit var adapter : AdapterSeveralDaysFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        severalDaysViewModel = ViewModelProvider(this).get(SeveralDaysViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_several_days, container, false)


        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        adapter = AdapterSeveralDaysFragment(severalDaysViewModel.list)
        recyclerView.adapter = adapter


        severalDaysViewModel.getSeveralDaysBD().observe(viewLifecycleOwner, { weatherDay ->
            Log.d("FFF", "answer")
            weatherDay?.let {
                //    favoriteCitiesViewModel.list = weatherDay.list!!
                replaceList(weatherDay.list, severalDaysViewModel.list)

                Log.d("FFF", "list size = " + weatherDay.list!!.size.toString())
                adapter.notifyDataSetChanged()
            }
        })

        return root
    }

    private fun replaceList(listAnswer: ArrayList<DataWeatherDay>?, listInAdapter: ArrayList<DataWeatherDay>, ){
        listInAdapter.clear()
        listAnswer?.forEach {
            listInAdapter.add(it)
        }
    }

}