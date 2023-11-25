package com.ismin.android
import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StationAdapter(
    private var stations: ArrayList<Station>,
    private val listener: StationAdapterListener)  :
    RecyclerView.Adapter<StationViewHolder>() {

    interface StationAdapterListener {
        fun onFavoriteClicked(id: Int)
        fun onStationClicked(station: Station)
    }

    private lateinit var favoriteButton: ImageButton

    private var isFavorite = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_station, parent,
            false)


        //favoriteButton.setOnClickListener {refreshData()}

        return StationViewHolder(row)
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewholder: StationViewHolder, position: Int) {
        val currentStation = stations[position]

        viewholder.ad_station.text = currentStation.n_station
        viewholder.nb_pdc.text = "${currentStation.nbre_pdc} place(s) disponible(s)"
        viewholder.acces_recharge.text = "Accès : ${currentStation.acces_recharge}"
        viewholder.accessbilite.text = "Accessibilité : ${currentStation.accessibilite}"

        viewholder.bind(currentStation)
        viewholder.itemView.setOnClickListener {
            listener.onStationClicked(currentStation)
        }

        if (currentStation.isFavorite) {
            viewholder.favoriteButton.setBackgroundResource(R.drawable.baseline_star_24)
        }
        else
        {
            viewholder.favoriteButton.setBackgroundResource(R.drawable.baseline_star_border_24)
        }

        viewholder.favoriteButton.setOnClickListener{
            if(isFavorite)
            {
                isFavorite = false
                viewholder.favoriteButton.setBackgroundResource(R.drawable.baseline_star_border_24)
            }
            else
            {
                isFavorite = true
                viewholder.favoriteButton.setBackgroundResource(R.drawable.baseline_star_24)
            }

            listener.onFavoriteClicked(currentStation.id)
        }
    }
}
