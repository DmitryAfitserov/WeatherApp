package com.app.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.viewmodel.FavoriteCitiesViewModel

class FavoriteCityFragment: Fragment() {

    private lateinit var favoriteCitiesViewModel: FavoriteCitiesViewModel
    private lateinit var adapter : AdapterFavoriteCities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteCitiesViewModel = ViewModelProvider(this).get(FavoriteCitiesViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite_city, container, false)


        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        adapter = AdapterFavoriteCities(favoriteCitiesViewModel.list)
        recyclerView.adapter = adapter


        favoriteCitiesViewModel.getWeatherDayBD().observe(viewLifecycleOwner, { weatherDay ->
            Log.d("FFF", "answer")
                weatherDay?.let {
                //    favoriteCitiesViewModel.list = weatherDay.list!!
                    replaceList(weatherDay.list, favoriteCitiesViewModel.list)

                    Log.d("FFF", "list size = " + weatherDay.list!!.size.toString())
                    adapter.notifyDataSetChanged()
                }

            })



        return root
    }

    private fun replaceList(listAnswer: ArrayList<ListData>?, listInAdapter: ArrayList<ListData>, ){
        listAnswer?.forEach {
            listInAdapter.add(it)
        }
    }

}