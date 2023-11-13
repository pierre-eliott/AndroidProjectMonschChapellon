package com.ismin.android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StationViewHolder(rootView: View): RecyclerView.ViewHolder(rootView) {
    var ad_station: TextView = rootView.findViewById(R.id.id_row_adresse)
    var nb_pdc: TextView = rootView.findViewById(R.id.id_row_nb_places)
    var acces_recharge: TextView = rootView.findViewById(R.id.id_row_type_acces)
    var accessbilite: TextView = rootView.findViewById(R.id.id_row_accessibilite)
}