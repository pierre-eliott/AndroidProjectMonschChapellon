package com.ismin.android
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater

class StationAdapter(private var stations: List<Station>) :
    RecyclerView.Adapter<StationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_station, parent,
            false)

        return StationViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: StationViewHolder, position: Int) {
        val (ad_station, nb_pdc, acces_recharge, accessbilite) = this.stations[position]

        viewholder.ad_station.text = ad_station
        viewholder.nb_pdc.text = nb_pdc
        viewholder.acces_recharge.text = acces_recharge
        viewholder.accessbilite.text = accessbilite
    }

    override fun getItemCount(): Int {
        return this.stations.size
    }

    fun refreshData(bookshelf: ArrayList<Station>)
    {
        this.stations = bookshelf
    }
}
