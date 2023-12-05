package com.ismin.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class StationInfoActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private var station: Station? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_info)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        if (intent.hasExtra("station")) {
            station = intent.getSerializableExtra("station") as? Station

            val addressTextView = findViewById<TextView>(R.id.id_info_adresse)
            val stationNameTextView = findViewById<TextView>(R.id.id_info_n_station)
            val accessTypeTextView = findViewById<TextView>(R.id.id_info_type_acces)
            val typePriseTextView = findViewById<TextView>(R.id.id_info_type_prise)
            val accessibilityTextView = findViewById<TextView>(R.id.id_info_accessibilite)
            val maxPowerTextView = findViewById<TextView>(R.id.id_info_puissance)
            val amenageurTextView = findViewById<TextView>(R.id.id_info_n_amenageur)
            val operateurTextView = findViewById<TextView>(R.id.id_info_n_operateur)
            val numPlacesTextView = findViewById<TextView>(R.id.id_info_nb_places)
            val commentsTextView = findViewById<TextView>(R.id.id_info_commentaires)

            station?.let {
                addressTextView.text = "Adresse: " + it.ad_station
                stationNameTextView.text = it.n_station
                accessTypeTextView.text = "Accès: " + it.acces_recharge
                accessibilityTextView.text = "Accessibilité: " + it.accessibilite
                typePriseTextView.text = "Type(s) de prise(s): " + it.type_prise
                maxPowerTextView.text = "Puissance max: " + it.puiss_max.toString() + " kW"
                amenageurTextView.text = "Aménageur: "+it.n_amenageur
                operateurTextView.text = "Opérateur: "+it.n_operateur
                numPlacesTextView.text = "Place(s) disponible(s): "+it.nbre_pdc.toString()
                commentsTextView.text = "Commentaire(s): \n\n" + it.observations
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        station?.let {
            val latitude = it.ylatitude
            val longitude = it.xlongitude
            val stationLocation = LatLng(latitude, longitude)

            if(it.isFavorite)
            {
                googleMap?.addMarker(MarkerOptions()
                    .position(stationLocation)
                    .title(it.n_station)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
            }
            else{
                googleMap?.addMarker(MarkerOptions()
                    .position(stationLocation)
                    .title(it.n_station)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
            }

            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(stationLocation, 9f))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        val backButton = menu.findItem(R.id.action_back)

        backButton.setOnMenuItemClickListener {
            onBackPressed()
            true
        }
        return true
    }

}