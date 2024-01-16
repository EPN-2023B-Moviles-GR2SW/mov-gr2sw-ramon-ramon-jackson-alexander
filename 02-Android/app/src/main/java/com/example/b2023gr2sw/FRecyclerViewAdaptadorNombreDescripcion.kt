package com.example.b2023gr2sw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion (
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder
        > () {
            inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                val nombreTextVIew: TextView
                val descripcionTextView: TextView
                val likesTextView: TextView
                val accionButton: Button
                var numeroLikes = 0

                init {
                    nombreTextVIew = view.findViewById(R.id.tv_nombre)
                    descripcionTextView = view.findViewById(R.id.tv_descripcion)
                    likesTextView = view.findViewById(R.id.tv_like)
                    accionButton = view.findViewById(R.id.btn_dar_like)
                    accionButton.setOnClickListener { anadirLike() }
                }

                fun anadirLike() {
                    numeroLikes = numeroLikes + 1
                    likesTextView.text = numeroLikes.toString()
                    contexto.aumentarTotalLikes()
                }
            }

    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    // Setear los datos para la interaccion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextVIew.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual.descripcion
        holder.likesTextView.text = "0"
        holder.accionButton.text = "ID:${entrenadorActual.id} Nombre: ${entrenadorActual.nombre}"
    }
}