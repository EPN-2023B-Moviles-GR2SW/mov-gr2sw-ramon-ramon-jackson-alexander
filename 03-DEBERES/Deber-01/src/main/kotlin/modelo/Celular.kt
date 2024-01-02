package modelo

class Celular(
    val modelo: String? = null,
    val sistemaOperativo: String? = null,
    val almacenamientoGB: Int? = null,
    val precio: Double? = null,
    val esGamer: Boolean? = null
    ) {

    init {
        this.modelo; this.sistemaOperativo; this.almacenamientoGB;
        this.precio; this.esGamer
    }

    fun mostrarCelular(listaCelulares: MutableList<Celular>?, modeloMostrar: String) {
        if (listaCelulares != null) {
            listaCelulares.forEach {
                if (it.modelo == modeloMostrar) {
                    println(it)
                    return
                } else {
                    println("El Modelo De Celular $modeloMostrar No Existe.")
                }
            }
        } else {
            println("La Marca No Tiene Modelos De Celulares.")
        }
    }

    fun mostrarCelularesDeUnaMarca(listaCelularesMarca: MutableList<Celular>?) {
        if (listaCelularesMarca != null) {
            listaCelularesMarca.forEach {
                print(it)
            }
        } else {
            println("La Marca No Tiene Modelos De Celulares.")
        }
    }

    fun actualizarCelular(marca: String, modeloActual: String, celularNuevo: Celular) {
        val indice = Marca().getByName(marca)?.listaCelulares?.indexOfFirst { it.modelo == modeloActual }
        if (indice != null) {
            Marca().getByName(marca)?.listaCelulares?.set(indice, celularNuevo)
            println("El Celular Se Ha Actualizado Exitosamente.")
        } else {
            println("La Marca $marca No Tiene El Celular $modeloActual Resgistrado.")
        }
    }

    fun eliminarCelular(marca: String, modeloEliminar: String) {
        val indiceEliminar = Marca().getByName(marca)?.listaCelulares?.indexOfFirst { it.modelo == modeloEliminar }
        if (indiceEliminar != null) {
            Marca().getByName(marca)?.listaCelulares?.removeAt(indiceEliminar)
            println("El Celular Se Ha Eliminado Exitosamente.")
        } else {
            println("La Marca $marca No Tiene El Celular $modeloEliminar Resgistrado.")
        }
    }

    override fun toString(): String {
        var celularToString: String = ""

        celularToString += "Modelo: '$modelo'\n"
        celularToString += "Sistema Operativo: $sistemaOperativo\n"
        celularToString += "Almacenamiento (GB): $almacenamientoGB\n"
        celularToString += "Precio: $precio\n"
        celularToString += "Tiene caracteristicas gamer: $esGamer\n\n"

        return celularToString
    }
}