package com.ismin.android

import java.io.Serializable

data class Station (
    val n_amenageur: String,
    val n_operateur: String,
    val id_station: String,
    val n_station: String,
    val ad_station: String,
    val code_insee: Int,
    val xlongitude: Double,
    val ylatitude: Double,
    val nbre_pdc: Int,
    val id_pdc: String,
    val puiss_max: Int,
    val type_prise: String,
    val acces_recharge: String,
    val accessibilite: String,
    val observations: String,
    val date_maj: String,
    val source: String,
    val region: String,
    val departement: String
): Serializable