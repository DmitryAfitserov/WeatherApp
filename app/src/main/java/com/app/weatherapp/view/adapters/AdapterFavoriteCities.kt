package com.app.weatherapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.model.weatherday.ListData
import com.app.weatherapp.ClickListener
import com.app.weatherapp.databinding.FavoriteCityItemBinding
import com.app.weatherapp.databinding.SeveralDaysItemBinding


class AdapterFavoriteCities(private var list: ArrayList<ListData>, var listener: ClickListener) :
    RecyclerView.Adapter<AdapterFavoriteCities.MyViewHolder>() {

    var par: ViewGroup? = null

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.favorite_city_item,
            parent,
            false
        )
        par = parent
        return MyViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding?.textViewWethName?.text = list[position].name
        holder.binding?.textViewWethDesc?.text = list[position].weather!![0].description

        holder.binding?.textViewWethvVis?.text =
            par?.context?.resources?.getString(R.string.weth_visi, list[position].visibility)
        holder.binding?.textViewWethTemp?.text =
            par?.context?.resources?.getString(R.string.weth_temp, list[position].main!!.temp)
        holder.binding?.textViewWethFeel?.text =
            par?.context?.resources?.getString(R.string.weth_feel, list[position].main!!.feelsLike)

    }


    class MyViewHolder(itemView: View, var listener: ClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var binding: FavoriteCityItemBinding? = null

        init {
            binding = DataBindingUtil.bind<FavoriteCityItemBinding>(itemView)
            binding?.imageBtnDelete?.setOnClickListener {
                listener.onPositionClicked(adapterPosition)
            }
        }

    }
}