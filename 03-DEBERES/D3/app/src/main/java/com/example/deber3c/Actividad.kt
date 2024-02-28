package com.example.deber3c

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Actividad : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        val rootView = inflater.inflate(R.layout.fragment_actividad, container, false)
        val adaptadorActividad = Adaptador_Actividad()

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.rv_actividad)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adaptadorActividad

        // Datos para el RecyclerView
        /*
        val titlesContactos = arrayOf("TRANS EXPRESS INC.", "JUAN GALINDEZ", "PEDRO QUINTANA",
            "ANDRES PEREZ", "BELEN JULIO")
        val imagesContactos = intArrayOf(R.drawable.icon_shop, R.drawable.icon_name_jg,
            R.drawable.icon_name_pq, R.drawable.icon_name_ap, R.drawable.icon_name_bj)*/

        return rootView
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            Actividad().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}