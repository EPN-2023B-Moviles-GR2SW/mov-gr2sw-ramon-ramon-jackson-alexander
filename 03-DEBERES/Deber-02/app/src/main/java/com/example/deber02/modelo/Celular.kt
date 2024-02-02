package com.example.deber02.modelo

class Celular (
    var modelo: String,
    val sistemaOperativo: String,
    var almacenamientoGB: Int,
    var precio: Double,
    var esGamer: Boolean
) {
    override fun toString(): String {
        return "Modelo: '$modelo', Sistema Operativo: '$sistemaOperativo', " +
                "Almacenamiento (GB): $almacenamientoGB, Precio: $precio, Es Gamer: $esGamer"
    }
}