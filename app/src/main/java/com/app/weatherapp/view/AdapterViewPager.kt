package com.app.weatherapp.view


import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.weatherapp.R


class AdapterViewPager(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {


        when(position){
            0 -> return MainCityFragment()
            1 -> return FavoriteCityFragment()
            2 -> return WeatherSeveralDaysFragment()
        }
        return MainCityFragment()
    }

}

//override fun getItem(position: Int): Fragment {
//        // getItem is called to instantiate the fragment for the given page.
//        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1)
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return context.resources.getString(TAB_TITLES[position])
//    }
//
//    override fun getCount(): Int {
//        // Show 2 total pages.
//        return 3
//    }


