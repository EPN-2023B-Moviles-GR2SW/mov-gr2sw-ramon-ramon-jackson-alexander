package controlador

import modelo.Celular
import modelo.Marca
import java.util.*

class MarcaCelularController {
    fun manejadorCrearMarca(nombre: String, fechaFundacion: Date,
                            cantidadModelos: Int, ingresosAnuales: Double) {
        val listaInicialCelulares: MutableList<Celular>? = null;
        val marcaNueva: Marca = Marca(nombre, fechaFundacion, cantidadModelos,
            ingresosAnuales, listaInicialCelulares)

        Marca().crearMarca(marcaNueva)
    }

    fun manejadorVisualizarMarca(tipo: Int, nombre: String) {
        if (tipo == 1) {
            // Para ver una especifica
            Marca().mostrarMarca(nombre)
        } else {
            Marca().mostrarListaMarcas()
        }

        println("Presione una tecla para volver.")
        val scanner = Scanner(System.`in`)
        val señal: String = scanner.nextLine()
        if (señal != null) {
            vista.marcasCelular()
        }
    }

    fun manejadorActualizarMarca(nombre: String, nombreN: String, fechaFundacionN: Date,
                                 cantidadModelosN: Int, ingresosAnualesN: Double,
                                 listaCelN: MutableList<Celular>?) {
        val marcaActualizada: Marca = Marca(nombreN, fechaFundacionN, cantidadModelosN, ingresosAnualesN, listaCelN)
        Marca().actualizarMarca(marcaActualizada, nombre)
    }

    fun manejadorEliminarMarca(nombre: String) {
        if (Marca().getByName(nombre) != null) {
            Marca().eliminarMarca(nombre)
        }
    }
}