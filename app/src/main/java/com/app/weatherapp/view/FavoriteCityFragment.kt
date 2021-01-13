package com.app.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.weatherapp.R
import com.app.weatherapp.viewmodel.MainCityViewModel

class FavoriteCityFragment: Fragment() {

    private lateinit var pagesViewModel: MainCityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagesViewModel = ViewModelProvider(this).get(MainCityViewModel::class.java)

        //     pagesViewModel = ViewModelProviders.of(activity!!).get(PagesViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        textView.text = "FavoriteCityFragment"


        return root
    }

}