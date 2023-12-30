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

        if (scanner.nextLine() != null) {
            vista.marcasCelular()
        }
        scanner.close()
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

    fun manejadorCrearCelular(marca: String, modelo: String, SO: String,
                              almacenamiento: Int, precio: Double, esGamer: Boolean) {
        val marcaConCelulares = Marca().getByName(marca)
        if (marcaConCelulares != null) {
            val nuevoCelular: Celular = Celular(modelo, SO, almacenamiento, precio, esGamer)

            if (marcaConCelulares.listaCelulares != null) {
                marcaConCelulares.listaCelulares!!.add(nuevoCelular)
            } else {
                marcaConCelulares.listaCelulares = mutableListOf()
                marcaConCelulares.listaCelulares!!.add(nuevoCelular)
            }

            Marca().actualizarMarca(marcaConCelulares, marca, 1)
        } else {
            println("Celular No Creado. La Marca $marca No Existe.")
        }
    }

    fun manejadorVisualizarCelular(tipo: Int, nombreMarca: String, modeloCelular: String) {
        val listaCelulares = Marca().getByName(nombreMarca)?.listaCelulares
        if (tipo == 1) {
            Celular().mostrarCelular(listaCelulares, modeloCelular)
        } else {
            Celular().mostrarCelularesDeUnaMarca(listaCelulares)
        }

        println("Presione una tecla para volver.")
        val scanner = Scanner(System.`in`)

        if (scanner.nextLine() != null) {
            vista.celulares()
        }
        scanner.close()
    }

    fun manejadorActualizarCelular(marca: String, modeloAct: String, modeloN: String, soN: String,
                                   almN: Int, precioN: Double, esGamerN: Boolean) {
        val celularActualizado: Celular = Celular(modeloN, soN, almN, precioN, esGamerN)
        Celular().actualizarCelular(marca, modeloAct, celularActualizado)
    }

    fun manejadorEliminarCelular(marca: String, modeloEliminar: String) {
        Celular().eliminarCelular(marca, modeloEliminar)
    }
}