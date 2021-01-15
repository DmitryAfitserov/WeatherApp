package com.app.weatherapp.view.adapters


import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.weatherapp.view.fragments.CustomPreferenceFragment
import com.app.weatherapp.view.fragments.FavoriteCityFragment
import com.app.weatherapp.view.fragments.MainCityFragment
import com.app.weatherapp.view.fragments.WeatherSeveralDaysFragment


class AdapterViewPager(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {


        when(position){
            0 -> return MainCityFragment()
            1 -> return FavoriteCityFragment()
            2 -> return WeatherSeveralDaysFragment()
            3 -> return CustomPreferenceFragment()
        }
        return MainCityFragment()
    }

}


