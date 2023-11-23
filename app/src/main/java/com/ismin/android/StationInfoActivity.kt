package com.ismin.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class StationInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_info)

        if (intent.hasExtra("station")) {
            val station = intent.getSerializableExtra("station") as? Station

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
                maxPowerTextView.text = "Puissance max: " + it.puiss_max.toString()
                amenageurTextView.text = "N° Oménageur: "+it.n_amenageur
                operateurTextView.text = "N° Opérateur: "+it.n_operateur
                numPlacesTextView.text = "Place(s) disponible(s): "+it.nbre_pdc.toString()
                commentsTextView.text = "Commentaire(s): \n\n" + it.observations
            }
        }

        val homeButton: ImageButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            onBackPressed() // Retour à l'activité parente
        }
    }
}