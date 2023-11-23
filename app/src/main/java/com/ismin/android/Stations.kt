package com.ismin.android

class Stations {
    private var stations = ArrayList<Station>();

    fun addStation(element : Station)
    {
        if(!stations.contains(element)) {
            stations.add(element)
        }
    }

    fun getAllStations(): List<Station> {
        return stations.sortedBy { it.ad_station }
    }

    fun sortedStationsByFavorites(): ArrayList<Station> {
        val favoriteStations = stations.filter { it.isFavorite }
        val nonFavoriteStations = stations.filter { !it.isFavorite }

        val sortedStations = ArrayList<Station>()
        sortedStations.addAll(favoriteStations)
        sortedStations.addAll(nonFavoriteStations)

        return sortedStations
    }



    fun deleteAllStations(){
        stations = arrayListOf<Station>()
    }

    fun getArrayList(): ArrayList<Station> {
        return stations
    }
}