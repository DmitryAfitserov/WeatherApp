package com.app.weatherapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.model.weatherday.ListData


class AdapterFavoriteCities(
    private var list: ArrayList<ListData>,
    var listener: ClickListener
) :
    RecyclerView.Adapter<AdapterFavoriteCities.MyViewHolder>() {


    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.favorite_city_item,
            parent,
            false
        )
        return MyViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cityName?.text = list[position].name
        holder.desc?.text = list[position].weather!![0].description
        holder.tempNow?.text = list[position].main!!.temp.toString()

//        holder.imageButtonDelete?.setOnClickListener(View.OnClickListener {
//            list.removeAt(position)
//            notifyDataSetChanged()
//        })

    }


    class MyViewHolder(itemView: View, var listener:ClickListener) : RecyclerView.ViewHolder(itemView){
        var cityName: TextView? = null
        var desc: TextView? = null
        var tempNow: TextView? = null
        var imageButtonDelete: ImageButton? = null

        init {
            cityName = itemView.findViewById(R.id.textViewCity)
            desc = itemView.findViewById(R.id.textViewDesc)
            tempNow = itemView.findViewById(R.id.textViewTempNow)
            imageButtonDelete = itemView.findViewById(R.id.image_btn_delete)

            imageButtonDelete?.setOnClickListener{
                listener.onPositionClicked(adapterPosition)
            }


        }

    }
}