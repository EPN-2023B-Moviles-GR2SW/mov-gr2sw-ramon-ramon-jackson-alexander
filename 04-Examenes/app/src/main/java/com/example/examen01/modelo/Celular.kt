package com.example.examen01.modelo

class Celular (
    val modelo: String,
    val sistemaOperativo: String,
    val almacenamientoGB: Int,
    val precio: Double,
    val esGamer: Boolean
) {
    override fun toString(): String {
        return "Modelo: '$modelo', Sistema Operativo: '$sistemaOperativo', " +
                "Almacenamiento (GB): $almacenamientoGB, Precio: $precio, Es Gamer: $esGamer"
    }
}