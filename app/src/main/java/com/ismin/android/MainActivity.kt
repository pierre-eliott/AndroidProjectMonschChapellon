package com.ismin.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismin.android.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, StationAdapter.StationAdapterListener {

    private val stations= Stations();

    private lateinit var binding: ActivityMainBinding

    private lateinit var refreshButton: ImageButton

    private lateinit var searchBar: EditText

    private lateinit var searchButton: ImageButton

    val SERVER_BASE_URL = "https://app-e0615a1c-398d-44a3-9bd8-96a6a3ef78db.cleverapps.io/"

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    val stationService = retrofit.create(StationService::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        val homeFragment= StationListFragment.newInstance(stations.getArrayList())
        val mapFragment=SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)
        val infoFragment=InfoFragment()

        refreshButton = findViewById(R.id.refreshButton)

        refreshButton.setOnClickListener {refreshData()}

        searchBar = findViewById(R.id.searchBar)

        searchButton = findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            val searchText: String = searchBar.text.toString()
            searchByName(searchText)
        }

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(homeFragment)
                R.id.map->setCurrentFragment(mapFragment)
                R.id.info->setCurrentFragment(infoFragment)
            }
            true
        }

        stationService.getAllStations()
            .enqueue(object : Callback<List<Station>> {
                override fun onResponse(
                    call: Call<List<Station>>,
                    response: Response<List<Station>>
                ) {
                    val allStations: List<Station>? = response.body()

                    if (allStations != null) {

                        for(elmnt in allStations) {

                            stations.addStation(elmnt)
                        }

                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    val fragment = StationListFragment.newInstance(stations.sortedStationsByFavorites())

                    fragmentTransaction.replace(R.id.flFragment, fragment)

                    fragmentTransaction.commit()
                    }
                }

                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    // DO THINGS
                    System.out.println("FAILURE - ADD Stations FROM SERVER")
                }
            })

    }


    private fun refreshData() {
        System.out.println("Test - Refresh Data")
        stations.deleteAllStations()
        stationService.getAllStations()
            .enqueue(object : Callback<List<Station>> {
                override fun onResponse(
                    call: Call<List<Station>>,
                    response: Response<List<Station>>
                ) {
                    val allStations: List<Station>? = response.body()

                    if (allStations != null) {

                        for(elmnt in allStations) {

                            stations.addStation(elmnt)
                        }

                        val fragmentTransaction = supportFragmentManager.beginTransaction()
                        val fragment = StationListFragment.newInstance(stations.sortedStationsByFavorites())

                        fragmentTransaction.replace(R.id.flFragment, fragment)

                        fragmentTransaction.commit()
                    }
                }

                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    // DO THINGS
                    System.out.println("FAILURE - ADD Stations FROM SERVER")
                }
            })
    }

    private fun searchByName(name: String) {
        System.out.println("Test - Search By Name: " + name)
        stations.deleteAllStations()
        val request = StationNameRequest(name = name)
        stationService.searchByName(request)
            .enqueue(object : Callback<List<Station>> {
                override fun onResponse(
                    call: Call<List<Station>>,
                    response: Response<List<Station>>
                ) {
                    val allStations: List<Station>? = response.body()

                    if (allStations != null) {

                        for(elmnt in allStations) {

                            stations.addStation(elmnt)
                        }

                        val fragmentTransaction = supportFragmentManager.beginTransaction()
                        val fragment = StationListFragment.newInstance(stations.sortedStationsByFavorites())

                        fragmentTransaction.replace(R.id.flFragment, fragment)

                        fragmentTransaction.commit()
                    }
                }

                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    // DO THINGS
                    System.out.println("FAILURE - ADD Stations FROM SERVER")
                }
            })
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            System.out.println("TEST - MAJ FRAGMENT")
            replace(R.id.flFragment, fragment)

            if (fragment is SupportMapFragment) {
                val mapFragment = SupportMapFragment.newInstance()
                mapFragment.getMapAsync(this@MainActivity)
                replace(R.id.flFragment, mapFragment)
            }
            when (fragment) {
                is StationListFragment -> refreshButton.visibility = View.VISIBLE
                else -> refreshButton.visibility = View.GONE
            }
            when (fragment) {
                is StationListFragment -> searchButton.visibility = View.VISIBLE
                else -> searchButton.visibility = View.GONE
            }
            when (fragment) {
                is StationListFragment -> searchBar.visibility = View.VISIBLE
                else -> searchBar.visibility = View.GONE
            }

            commit()
        }
    }

    override fun onMapReady(gmap: GoogleMap) {
        //System.out.println("TEST - MAP")

        val posCamera = LatLng(47.099419, 2.349404 )
        val zoomLevel = 5.0f
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(posCamera, zoomLevel)

        gmap.moveCamera(cameraUpdate)

        for(elmnt in stations.getArrayList())
        {
            val pos = LatLng(elmnt.ylatitude, elmnt.xlongitude)
            gmap.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title(elmnt.ad_station)
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_foreground))
            )
        }
    }

    override fun onFavoriteClicked(id: Int) {
        System.out.println("Test - ADD REMOVE FAVORITES id : " + id)
        stations.deleteAllStations()
        val request = StationIdRequest(id = id)
        stationService.addRemoveFavorit(request)
            .enqueue(object : Callback<List<Station>> {
                override fun onResponse(
                    call: Call<List<Station>>,
                    response: Response<List<Station>>
                ) {
                    val allStations: List<Station>? = response.body()

                    if (allStations != null) {

                        for(elmnt in allStations) {

                            stations.addStation(elmnt)
                        }

                        val fragmentTransaction = supportFragmentManager.beginTransaction()
                        val fragment = StationListFragment.newInstance(stations.sortedStationsByFavorites())

                        fragmentTransaction.replace(R.id.flFragment, fragment)

                        fragmentTransaction.commit()
                    }
                }

                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    // DO THINGS
                    System.out.println("FAILURE - ADD / REMOVE Stations FROM favorites")
                }
            })
    }

    override fun onStationClicked(station: Station) {
        val intent = Intent(this, StationInfoActivity::class.java)
        intent.putExtra("station", station)
        startActivity(intent)
    }
}