package com.ismin.android

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
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
import java.io.Console
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() , OnMapReadyCallback {

    private val stations= Stations();

    private lateinit var binding: ActivityMainBinding

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
                    val fragment = StationListFragment.newInstance(stations.getArrayList())

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
}