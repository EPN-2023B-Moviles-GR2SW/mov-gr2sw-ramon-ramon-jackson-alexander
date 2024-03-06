package com.example.proyecto2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCategoria: RecyclerView.Adapter<AdaptadorCategoria.ViewHolder>() {
    val titles = arrayOf("Entretenimiento",
        "Hogar",
        "Tecnología")

    val images = intArrayOf(R.drawable.card2_1,
        R.drawable.card2_2,
        R.drawable.card2_3)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_cat_mas_vendidas, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemImage.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView

        init {
            itemTitle = itemView.findViewById(R.id.tv_categoria)
            itemImage = itemView.findViewById(R.id.img_categoria)
        }
    }
}