package modelo

import java.util.*

class Marca (
    protected val nombre: String? = null,
    protected val fechaFundacion: Date? = null,
    protected val cantidadModelos: Int? = null,
    protected val ingresosAnuales: Double? = null,
    var listaCelulares: MutableList<Celular>? = null
    ) {

    init {
        this.nombre; this.fechaFundacion; this.cantidadModelos;
        this.ingresosAnuales; this.listaCelulares
    }

    fun crearMarca(marca: Marca){
        // Poner el codigo para guardar en los archivos
        listaMarcas.add(marca)
        println("Marca Creada Exitosamente.")
    }

    fun getByName(nombreMarca: String): Marca? {
        var marcaEncontrada: Marca? = null

        // forEach para recorrer la lista de marcas
        listaMarcas.forEach {
            if (it.nombre == nombreMarca) {
                // Si encuentra una coincidencia, asigna la marca y termina el bucle
                marcaEncontrada = it
                return@forEach
            }
        }

        // No se encontro ninguna coincidencia
        return marcaEncontrada
    }

    fun actualizarMarca(marcaModifcada: Marca, nombreMarcaAnterior: String, tipo: Int? = 0){
        val indice = listaMarcas.indexOfFirst { it.nombre == nombreMarcaAnterior }
        listaMarcas[indice] = marcaModifcada
        if (tipo == 1) {
            println("Celular Creado Exitosamente")
        } else {
            println("Marca Actualizada Exitosamente.")
        }
    }

    fun eliminarMarca(nombre: String) {
        listaMarcas.removeAt(listaMarcas.indexOfFirst { it.nombre == nombre })
        // Ver si se debe hacer algo con la lista de celulares
        println("La Marca Y Sus Celulares Se Han Eliminado Exitosamente.")
    }

    fun mostrarMarca(nombre: String) {
        if (getByName(nombre) != null) {
            println(getByName(nombre))
            return
        } else {
            println("La Marca $nombre No Existe")
        }
    }

    fun mostrarListaMarcas() {
        if (getListaMarcas().isEmpty()) {
            println("No Existen Marcas Registradas.")
        } else {
            getListaMarcas().forEach { marca ->
                println(marca)
            }
        }
    }

    fun mostrarListaMarcaYCelulares() {
        if (getListaMarcas().isEmpty()) {
            println("No Existen Marcas Registradas.")
        } else {
            var elementosLista: String = ""
            getListaMarcas().forEach { marca ->
                elementosLista += "---- " + marca.nombre + " ----\n"
                if (marca.listaCelulares != null) {
                    for (celular in marca.listaCelulares!!) {
                        elementosLista += celular.toString()
                    }
                } else {
                    elementosLista += "Esta Marca No Tiene Celulares Registrados.\n"
                }

                println(elementosLista)
                elementosLista = ""
            }
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

        marcaToString += "******************************************************\n"
        marcaToString += "Nombre: '$nombre'\n"
        marcaToString += "Fecha de Fundacion: $fechaFundacion\n"
        marcaToString += "Cantidad de Modelos: $cantidadModelos \n"
        marcaToString += "Ingresos Anuales: $ingresosAnuales\n\n"
        marcaToString += "   ---Lista De Celulares---\n"

        if (listaCelulares != null) {
            for (elemento in listaCelulares!!) {
                marcaToString += elemento.toString()
            }
        } else {
            marcaToString += "Esta Marca No Tiene Celulares Registrados.\n"
        }

        return marcaToString
    }
}