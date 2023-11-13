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
        val (n_amenageur,n_operateur , id_station, n_station, ad_station,code_insee, xlongitude, ylatitude, nbre_pdc, id_pdc, puiss_max, type_prise, acces_recharge, accessibilite, observations, date_maj, source, region,departement) = this.stations[position]

        viewholder.ad_station.text = ad_station
        viewholder.nb_pdc.text = nbre_pdc.toString() + " place(s) disponible(s)"
        viewholder.acces_recharge.text = "Acces : " + acces_recharge
        viewholder.accessbilite.text ="Accessibilite : " +  accessibilite
    }

    override fun getItemCount(): Int {
        return this.stations.size
    }

    fun refreshData(bookshelf: ArrayList<Station>)
    {
        this.stations = bookshelf
    }
}
