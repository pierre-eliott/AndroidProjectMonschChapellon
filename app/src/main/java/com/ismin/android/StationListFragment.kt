package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

class StationListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: ArrayList<Station>? = null
    private lateinit var recyclerView: StationViewHolder
    private lateinit var adapter:StationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as ArrayList<Station>
        }

        val rootView = inflater.inflate(R.layout.fragment_station_list, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.id_main_list)
        adapter = StationAdapter(param1!!)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager

        return rootView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StationListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: ArrayList<Station>) =
            StationListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}