package com.example.examen01.modelo

import java.util.Date

class Marca (
    var nombre: String,
    var fechaFundacion: Date,
    var cantidadModelos: Int,
    var ingresosAnuales: Double,
    var listaCelulares: ArrayList<Celular>
) {
    override fun toString(): String {
        return "Nombre: '$nombre', Fecha de Fundacion: $fechaFundacion, " +
                "Cantidad de Modelos: $cantidadModelos, Ingresos Anuales: $ingresosAnuales"
    }
}