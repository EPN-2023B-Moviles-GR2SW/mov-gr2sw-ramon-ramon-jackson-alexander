package com.example.deber3c

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptador_Prox_Viaje: RecyclerView.Adapter<Adaptador_Prox_Viaje.ViewHolder>() {
    val images = intArrayOf(R.drawable.img_rv_card1,
        R.drawable.img_rv_card2,
        R.drawable.img_rv_card3,
        R.drawable.img_rv_card4)

    val titles = arrayOf("Para grupos grandes →",
        "Reserva viajes de trabajo →",
        "Planea tus salidas →",
        "Reserva un auto para eventos →")

    val descriptions = arrayOf("Espacio para todo el grupo y el equipaje",
        "Ideal para reuniones de negocios",
        "Reserva un viaje con anticipación",
        "Llega al punto de encuentro a la hora acordada")

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_proximo_viaje, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemImage.setImageResource(images[i])
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDescription.text = descriptions[i]
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDescription: TextView

        init {
            itemImage = itemView.findViewById(R.id.img_grupos_grandes)
            itemTitle = itemView.findViewById(R.id.tv_grupos_grandes)
            itemDescription = itemView.findViewById(R.id.tv_grupos_grandes_desc)
        }
    }
}