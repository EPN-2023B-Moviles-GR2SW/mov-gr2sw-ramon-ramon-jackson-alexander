package com.example.deber02.modelo

class Marca (
    var nombre: String,
    var fechaFundacion: String,
    var cantidadModelos: Int,
    var ingresosAnuales: Double,
    var listaCelulares: ArrayList<Celular>
) {
    override fun toString(): String {
        return "Nombre: '$nombre', Fecha de Fundacion: $fechaFundacion, " +
                "Cantidad de Modelos: $cantidadModelos, Ingresos Anuales: $ingresosAnuales"
    }
}