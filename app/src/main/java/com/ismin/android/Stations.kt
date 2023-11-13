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

    fun deleteAllStations(){
        stations = arrayListOf<Station>()
    }

    fun getArrayList(): ArrayList<Station> {
        return stations
    }
}