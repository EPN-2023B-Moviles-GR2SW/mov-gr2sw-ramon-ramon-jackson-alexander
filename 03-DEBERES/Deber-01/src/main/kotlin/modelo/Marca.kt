package modelo

import java.util.*

class Marca (
    protected val nombre: String? = null,
    protected val fechaFundacion: Date? = null,
    protected val cantidadModelos: Int? = null,
    protected val ingresosAnuales: Double? = null,
    protected val listaCelulares: MutableList<Celular>? = null
    ) {

    init {
        this.nombre; this.fechaFundacion; this.cantidadModelos;
        this.ingresosAnuales; this.listaCelulares
    }

    fun crearMarca(marca: Marca){
        // Poner el codigo para guardar en los archivos
        listaMarcas.add(marca)
    }

    fun getByName(nombreMarca: String): Marca{
        var marcaEncontrada: Marca? = null

        // forEach para recorrer la lista de marcas
        listaMarcas.forEach {
            if (it.nombre == nombreMarca) {
                // Si encuentra una coincidencia, asigna la marca y termina el bucle
                marcaEncontrada = it
                return@forEach
            }
        }

        // No se encontro ninguna coincidencia, CAMBIAR ESTO y complementar esto en el Controlador
        return marcaEncontrada ?: throw NoSuchElementException("No se encontró una marca con el nombre $nombreMarca")
    }

    fun actualizarMarca(marcaModifcada: Marca, nombreMarcaAnterior: String){
        val indice = listaMarcas.indexOfFirst { it.nombre == nombreMarcaAnterior }
        listaMarcas[indice] = marcaModifcada
    }

    fun eliminarMarca(nombre: String) {
        listaMarcas.removeAt(listaMarcas.indexOfFirst { it.nombre == nombre })
    }

    fun mostrarMarca(nombre: String) {
        println(getByName(nombre))
    }

    fun mostrarListaMarcas() {
        getListaMarcas().forEach { marca ->
            println(marca)
            println()
        }
    }

    companion object {
        private val listaMarcas = mutableListOf<Marca>()

        fun getListaMarcas(): List<Marca> {
            if (listaMarcas != null) {
                return listaMarcas
            }
            return getListaMarcas()
        }
    }

    override fun toString(): String {
        var marcaToString: String = ""

        marcaToString += "Nombre: '$nombre'\n"
        marcaToString += "Fecha de Fundacion: $fechaFundacion\n"
        marcaToString += "Cantidad de Modelos: $cantidadModelos \n"
        marcaToString += "Ingresos Anuales: $ingresosAnuales\n"
        // marcaToString += println(listaCelulares)

        return marcaToString
    }
}