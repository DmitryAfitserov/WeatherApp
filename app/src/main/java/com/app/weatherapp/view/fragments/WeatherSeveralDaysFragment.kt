package com.app.weatherapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.databinding.FragmentSeveralDaysBinding
import com.app.weatherapp.view.adapters.AdapterSeveralDaysFragment
import com.app.weatherapp.viewmodel.SeveralDaysViewModel

class WeatherSeveralDaysFragment : Fragment() {

    private lateinit var severalDaysViewModel: SeveralDaysViewModel
    private lateinit var adapter: AdapterSeveralDaysFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        severalDaysViewModel = ViewModelProvider(this).get(SeveralDaysViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  val root = inflater.inflate(R.layout.fragment_several_days, container, false)

        val binding: FragmentSeveralDaysBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_several_days, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        adapter = AdapterSeveralDaysFragment(severalDaysViewModel.list)
        binding.recyclerView.adapter = adapter

        severalDaysViewModel.getSeveralDaysBD().observe(viewLifecycleOwner, { weatherSeveralDay ->
            weatherSeveralDay?.let {
                severalDaysViewModel.livaDataSeveralDays.value = weatherSeveralDay
                severalDaysViewModel.prepareList(weatherSeveralDay)

                adapter.notifyDataSetChanged()
            }
        })


        binding.viewmodel = severalDaysViewModel

        return binding.root
    }


}