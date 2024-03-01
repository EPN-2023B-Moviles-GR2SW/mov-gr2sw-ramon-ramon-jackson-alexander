package com.example.examen02.model

data class Celular(
    val id: Int?,
    var name: String?,
    var price: Double?,
    var stock: Int?,
    var isAvailable: Boolean? = false,
    var brandId: String?
) {

    constructor() : this(null, null, null, null, false, null)

    override fun toString(): String {
        return "Nombre: ${name}\nPrecio: ${price}\nDisponible: ${if (isAvailable == true) "Si" else "No"}"
    }
}