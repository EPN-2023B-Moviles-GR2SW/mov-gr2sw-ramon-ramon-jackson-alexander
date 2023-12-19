package modelo

import java.util.NoSuchElementException

class Celular(
    protected val modelo: String,
    protected val sistemaOperativo: String,
    protected val almacenamientoGB: Int,
    protected val precio: Double,
    protected val esGamer: Boolean,
    ) {

    // Para probar el funcionamiento
    val listaCelulares: MutableList<Celular>
        get(): MutableList<Celular> {
            // Poner la logica para recuperar los celulares del archivo y crear la lista
            return listaCelulares
        }

    init {
        this.modelo; this.sistemaOperativo; this.almacenamientoGB;
        this.precio; this.esGamer
    }

    fun crearCelular(celular: Celular){
        // Poner el codigo para guardar en los archivos
        listaCelulares.add(celular)
    }

    fun getByModel(modelo: String): Celular{
        var celularEncontrado: Celular? = null

        // forEach para recorrer la lista de celulares
        listaCelulares.forEach {
            if (it.modelo == modelo) {
                // Si encuentra una coincidencia, asigna la marca y termina el bucle
                celularEncontrado = it
                return@forEach
            }
        }

        // No se encontro ninguna coincidencia, CAMBIAR ESTO y complementar esto en el Controlador
        return celularEncontrado ?: throw NoSuchElementException("No se encontró un celular con el modelo $modelo")
    }

    fun actualizarCelular(celularModifcado: Celular, modeloCelularAnterior: String){
        val indice = listaCelulares.indexOfFirst { it.modelo == modeloCelularAnterior }
        listaCelulares[indice] = celularModifcado
    }

    fun eliminarCelular(modelo: String) {
        listaCelulares.removeAt(listaCelulares.indexOfFirst { it.modelo == modelo })
    }

    override fun toString(): String {
        var celularToString: String = ""

        celularToString += "Modelo:' $modelo'\n"
        celularToString += "Sistema Operativo: $sistemaOperativo\n"
        celularToString += "Almacenamiento (GB): $almacenamientoGB\n"
        celularToString += "Precio: $precio\n"
        celularToString += "Tiene caracteristicas gamer: $esGamer\n"

        return celularToString
    }
}