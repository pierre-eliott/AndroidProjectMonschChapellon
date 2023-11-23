package com.ismin.android

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StationViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    private var station: Station? = null // Assurez-vous que Station est une classe accessible ici

    init {
        rootView.setOnClickListener {
            station?.let { selectedStation ->
                val intent = Intent(rootView.context, StationInfoActivity::class.java)
                intent.putExtra("station_id", selectedStation.id)
                rootView.context.startActivity(intent)
            }
        }
    }

    fun bind(station: Station) {
        this.station = station
    }

    var ad_station: TextView = rootView.findViewById(R.id.id_row_adresse)
    var nb_pdc: TextView = rootView.findViewById(R.id.id_row_nb_places)
    var acces_recharge: TextView = rootView.findViewById(R.id.id_row_type_acces)
    var accessbilite: TextView = rootView.findViewById(R.id.id_row_accessibilite)
    var favoriteButton: ImageButton = rootView.findViewById(R.id.favoriteButton)
}

