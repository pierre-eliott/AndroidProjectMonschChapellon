package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var appAuthorTextView: TextView
    private lateinit var appTitleTextView: TextView
    private lateinit var appSourceTextView: TextView
    private lateinit var appLinkTextView: TextView
    private lateinit var appInfosTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        // Trouver les TextViews dans le layout du fragment
        appAuthorTextView = view.findViewById(R.id.id_app_author)
        appTitleTextView = view.findViewById(R.id.id_app_title)
        appSourceTextView = view.findViewById(R.id.id_app_source)
        appLinkTextView = view.findViewById(R.id.id_app_link)
        appInfosTextView = view.findViewById(R.id.id_app_infos)

        // Assigner les valeurs aux TextViews
        appAuthorTextView.text = "©Pierre-Eliott Monsch & Léa Chapellon"
        appTitleTextView.text = "JuiceUp Spot"
        appSourceTextView.text = "Source: OpenData Réseaux-Energies"
        appLinkTextView.text = "Lien: https://odre.opendatasoft.com/api/explore/v2.1/catalog/datasets/bornes-irve/records?limit=100"
        appInfosTextView.text = "Cette application rassemble les informations relatives aux bornes de recharge de la région disponibles dans la source. \n\n Ces informations sont entre autre: \n\n - Nom de la station \n - Adresse de la station \n -Type d'accès \n - Accessibilité \n - Type(s) de prise(s) \n - Puissance maximale disponible \n - Nombre de places dans la station \n -Numéro de l'aménageur \n - Numéro de l'opérateur \n -Commentaires sur la station \n - Position GPS"

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}