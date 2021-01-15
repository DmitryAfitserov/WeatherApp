package com.app.weatherapp.view.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.databinding.SeveralDaysItemBinding
import com.app.weatherapp.model.weatherseveralday.DataWeatherDay


class AdapterSeveralDaysFragment(private var list: List<DataWeatherDay>) :
    RecyclerView.Adapter<AdapterSeveralDaysFragment.MyViewHolder>() {

    var par: ViewGroup? = null

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        par = parent
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.several_days_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding?.textSeveralDesc?.text =  list[position].weather!![0].description
        holder.binding?.textSeveralDate?.text = list[position].dt


        holder.binding?.textSeveralMorn?.text = par?.context?.resources?.getString(R.string.sev_morn, list[position].temp!!.morn)
        holder.binding?.textSeveralDay?.text = par?.context?.resources?.getString(R.string.sev_day, list[position].temp!!.day)
        holder.binding?.textSeveralEve?.text = par?.context?.resources?.getString(R.string.sev_eve, list[position].temp!!.eve)
        holder.binding?.textSeveralNig?.text = par?.context?.resources?.getString(R.string.sev_night,  list[position].temp!!.night)
        holder.binding?.textSeveralPressure?.text = par?.context?.resources?.getString(R.string.sev_pres,   list[position].pressure)
        holder.binding?.textSeveralHumid?.text = par?.context?.resources?.getString(R.string.sev_hun,  list[position].humidity)
        holder.binding?.textSeveralWind?.text = par?.context?.resources?.getString(R.string.sev_wind,   list[position].speed)
        holder.binding?.textSeveralCl?.text = par?.context?.resources?.getString(R.string.sev_cl,   list[position].clouds)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding : SeveralDaysItemBinding? = null


        init {
            binding = DataBindingUtil.bind<SeveralDaysItemBinding>(itemView)
        }
    }
}