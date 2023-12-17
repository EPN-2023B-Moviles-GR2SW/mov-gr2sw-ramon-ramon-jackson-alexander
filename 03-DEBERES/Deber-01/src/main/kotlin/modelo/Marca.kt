package modelo

import java.util.*

class Marca (
    protected val nombre: String,
    protected val fechaFundacion: Date,
    protected val cantidadModelos: Int,
    protected val ingresosAnuales: Double,
    protected val listaCelulares: MutableList<Celular>
    ) {

    init {
        this.nombre; this.fechaFundacion; this.cantidadModelos;
        this.ingresosAnuales; this.listaCelulares
    }
    
}