package com.app.weatherapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay


class AdapterSeveralDaysFragment(private var list: List<DataWeatherDay>) :
    RecyclerView.Adapter<AdapterSeveralDaysFragment.MyViewHolder>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.several_days_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView?.text = list[position].feelsLike.toString()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var largeTextView: TextView? = null

        init {
            largeTextView = itemView.findViewById(R.id.textView)
        }
    }
}