package com.app.weatherapp.view.fragments

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
import com.app.weatherapp.ClickListener
import com.app.weatherapp.view.adapters.AdapterFavoriteCities
import com.app.weatherapp.viewmodel.FavoriteCitiesViewModel

class FavoriteCityFragment : Fragment() {

    private lateinit var favoriteCitiesViewModel: FavoriteCitiesViewModel
    private lateinit var adapter: AdapterFavoriteCities
    private var fragmentExist = false

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

        adapter =
            AdapterFavoriteCities(favoriteCitiesViewModel.listToAdapter, object : ClickListener {
                override fun onPositionClicked(position: Int) {
                    favoriteCitiesViewModel.deletePosition(position)

                }
            })
        recyclerView.adapter = adapter

        favoriteCitiesViewModel.getMainCityBD().observe(viewLifecycleOwner, { mainCity ->
            mainCity?.let {
                observerBD()
            }
        })

        favoriteCitiesViewModel.getWeatherDayBD().observe(viewLifecycleOwner, { weatherDay ->
            weatherDay?.let {
                observerBD()
            }
        })

        return root
    }

    private fun observerBD() {
        favoriteCitiesViewModel.prepareList()
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        favoriteCitiesViewModel.liveDataMainCity.removeObservers(viewLifecycleOwner)
        favoriteCitiesViewModel.liveDataWeatherDay.removeObservers(viewLifecycleOwner)
        super.onPause()

    }

    override fun onResume() {
        if (fragmentExist) {
            favoriteCitiesViewModel.getMainCityBD().observe(viewLifecycleOwner, { mainCity ->
                mainCity?.let {
                    observerBD()
                }
            })

            favoriteCitiesViewModel.getWeatherDayBD().observe(viewLifecycleOwner, { weatherDay ->
                weatherDay?.let {
                    observerBD()
                }
            })
        } else {
            fragmentExist = true
        }
        super.onResume()

    }
}