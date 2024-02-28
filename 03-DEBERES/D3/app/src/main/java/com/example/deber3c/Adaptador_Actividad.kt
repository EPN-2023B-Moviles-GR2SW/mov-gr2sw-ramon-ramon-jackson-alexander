package com.example.deber3c

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptador_Actividad: RecyclerView.Adapter<Adaptador_Actividad.ViewHolder>() {
    val directions = arrayOf(
        "Astudillo",
        "Cruz Vital (Cruz Roja)",
        "Terminal terreste Carcelen",
        "El portal",
        "Astudillo",
        "Cruz Vital (Cruz Roja)",
        "Terminal terreste Carcelen",
        "El portal"
    )

    val datesAndTimes = arrayOf(
        "8 dic - 9:33 pm",
        "6 oct - 6:26 pm",
        "25 sept - 7:38 pm",
        "8 oct - 4:40 pm",
        "8 dic - 9:33 pm",
        "6 oct - 6:26 pm",
        "25 sept - 7:38 pm",
        "8 oct - 4:40 pm"
    )

    val prices = arrayOf(
        "$2.00",
        "$5.40",
        "$2.14",
        "$2.00",
        "$2.00",
        "$5.40",
        "$2.14",
        "$2.00"
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_actividad, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemDireccion.text = directions[i]
        viewHolder.itemFechaHora.text = datesAndTimes[i]
        viewHolder.itemPrecio.text = prices[i]
    }

    override fun getItemCount(): Int {
        return directions.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemDireccion: TextView
        var itemFechaHora: TextView
        var itemPrecio: TextView

        init {
            itemDireccion = itemView.findViewById(R.id.txt_direccion)
            itemFechaHora = itemView.findViewById(R.id.txt_fecha_hora)
            itemPrecio = itemView.findViewById(R.id.txt_precio)
        }
    }
}