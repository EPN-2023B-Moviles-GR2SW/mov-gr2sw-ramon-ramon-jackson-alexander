package vista

import controlador.MarcaCelularController
import modelo.Celular
import modelo.Marca
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun main(){
    if (File("marcas.txt").exists() &&
        File("celulares.txt").exists()) {
        // Leer los datos de los archivos y cargarlos al programa
        Marca().cargarMarcasYCelulares()
    }

    val lectorDatos = Scanner(System.`in`)

    var bienvenida: String = ""
    bienvenida += "Bienvenido al Sistema de Gestion de Marcas de Celulares\n\n"
    bienvenida += "Seleccione una opcion para iniciar:\n"
    bienvenida += "1. Gestionar Marcas y Celulares\n"
    bienvenida += "2. Visualizar Modelos de Celulares\n"
    bienvenida += "3. Salir\n"
    println(bienvenida)

    print("-> ")
    val opcionMenu = lectorDatos.nextLine()
    println()
    when (opcionMenu) {
        ("1") -> {
            gestionarMYC()
        }
        ("2") -> {
            visualizarModelos()
        }
        ("3") -> {
            return
        }
        else -> {
            main()
        }
    }
    lectorDatos.close()

    // Guardar los nuevos datos en los archivos
    guardarMarcasYCelulares()
}

fun gestionarMYC() {
    print("En este apartado podra gestionar los datos de una Marca\nasi como, los modelos" +
            " de celulares que pertenezcan a esta.\n\nA continuacion, se presentan las opciones " +
            "disponibles:\n")

    var opcionesGestionMYC: String = ""
    opcionesGestionMYC += "1. Marcas de celular\n"
    opcionesGestionMYC += "2. Celulares\n"
    opcionesGestionMYC += "3. Regresar\n"
    println(opcionesGestionMYC)

    val lectorDatos = Scanner(System.`in`)
    print("Ingrese una opcion -> ")
    val opcionMenu = lectorDatos.nextLine()
    when (opcionMenu) {
        ("1") -> {
            marcasCelular()
        }
        ("2") -> {
            celulares()
        }
        ("3") -> {
            main()
        }
        else -> {
           gestionarMYC()
        }
    }
}

fun marcasCelular(){
    print("\nSeleccione una de las siguientes opciones:\n")

    var opcionesMarcasCelular: String = ""
    opcionesMarcasCelular += "1. Crear una Marca\n"
    opcionesMarcasCelular += "2. Visualizar los datos de las Marcas\n"
    opcionesMarcasCelular += "3. Actualizar los datos de una Marca\n"
    opcionesMarcasCelular += "4. Eliminar una Marca\n"
    opcionesMarcasCelular += "5. Regresar\n"
    println(opcionesMarcasCelular)

    val lectorDatos = Scanner(System.`in`)
    print("-> ")
    val opcionMenu = lectorDatos.nextLine()
    val controladorMarca: MarcaCelularController = MarcaCelularController()
    when (opcionMenu) {
        ("1") -> {
            print("\nRegistrar Una Nueva Marca\n\nIngrese los datos solicitados.\n")

            print("Nombre: ")
            val nombre = lectorDatos.nextLine()

            print("Fecha de Fundacion (dd/MM/yyyy): ")
            val fechaFundOrg = lectorDatos.nextLine()
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            var fechaFundFinal: Date = Date()
            try {
                fechaFundFinal = formatoFecha.parse(fechaFundOrg)
            } catch (e: Exception) {
                println("Error al convertir la fecha")
            }

            print("Cantidad de Modelos: ")
            val cantidadModelos: Int = lectorDatos.nextLine().toInt()

            print("Ingresos Anuales: ")
            val ingresosAnuales: Double = lectorDatos.nextLine().toDouble()

            controladorMarca.manejadorCrearMarca(nombre, fechaFundFinal, cantidadModelos, ingresosAnuales)
            marcasCelular()
        }
        ("2") -> {
            var opcionesVerMarca: String = ""
            opcionesVerMarca += "\nSeleccione una opcion:\n"
            opcionesVerMarca += "1. Visualizar una Marca especifica\n"
            opcionesVerMarca += "2. Visualizar todas las Marcas\n"
            opcionesVerMarca += "3. Regresar\n"
            println(opcionesVerMarca)

            print("-> ")
            val opcionVisualizar = lectorDatos.nextLine()
            when (opcionVisualizar) {
                ("1") -> {
                    print("\nIngrese el nombre de la Marca: ")
                    val marcaVisualizar: String = lectorDatos.nextLine()

                    // Llamar al controlador
                    controladorMarca.manejadorVisualizarMarca(1, marcaVisualizar)
                }
                ("2") -> {
                    controladorMarca.manejadorVisualizarMarca(2, "")
                }
                else -> {
                    marcasCelular()
                }
            }
        }
        ("3") -> {
            print("\nActualizar Datos De Una Marca.\n\nIngrese el nombre de la Marca a actualizar.\n")

            print("Nombre: ")
            val nombre = lectorDatos.nextLine()

            if (Marca().getByName(nombre) != null) {
                print("Ingrese los nuevos datos para la Marca:\n")

                print("Nombre: ")
                val nombreAct = lectorDatos.nextLine()

                print("Fecha de Fundacion (dd/MM/yyyy): ")
                val fechaFundOrg = lectorDatos.nextLine()
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
                var fechaFundFinalAct: Date = Date()
                try {
                    fechaFundFinalAct = formatoFecha.parse(fechaFundOrg)
                } catch (e: Exception) {
                    println("Error al convertir la fecha")
                }

                print("Cantidad de Modelos: ")
                val cantidadModelosAct: Int = lectorDatos.nextLine().toInt()

                print("Ingresos Anuales: ")
                val ingresosAnualesAct: Double = lectorDatos.nextLine().toDouble()

                val listaCel = Marca().getByName(nombre)?.listaCelulares

                controladorMarca.manejadorActualizarMarca(nombre, nombreAct, fechaFundFinalAct,
                    cantidadModelosAct, ingresosAnualesAct, listaCel)

                marcasCelular()
            } else {
                println("La Marca $nombre No Existe.")
                marcasCelular()
            }
        }
        ("4") -> {
            print("\nEliminar Una Marca\n")
            print("IMPORTANTE: Al eliminar una Marca tambien se eliminaran sus celulares.\n\n")
            print("Ingrese el nombre de la Marca a eliminar.\n")

            print("Nombre: ")
            val nombre = lectorDatos.nextLine()

            controladorMarca.manejadorEliminarMarca(nombre)
            marcasCelular()
        }
        else -> {
            gestionarMYC()
        }
    }
}

fun celulares() {
    val lectorDatos = Scanner(System.`in`)

    print(
        "\nPara usar este apartado es necesario ingresar el nombre de la marca\n" +
                "a la que pertenecen los celulares\n"
    )
    print("Nombre de la Marca: ")
    val nombreMarca = lectorDatos.nextLine()

    if (Marca().getByName(nombreMarca) != null) {

        print("\nSeleccione una de las siguientes opciones:\n")

        var opcionesCelulares: String = ""
        opcionesCelulares += "1. Crear un Celular\n"
        opcionesCelulares += "2. Visualizar los datos de los Celulares\n"
        opcionesCelulares += "3. Actualizar los datos de un Celular\n"
        opcionesCelulares += "4. Eliminar un Celular\n"
        opcionesCelulares += "5. Regresar\n"
        println(opcionesCelulares)

        print("-> ")
        val opcionMenu = lectorDatos.nextLine()
        val controladorCelular: MarcaCelularController = MarcaCelularController()
        when (opcionMenu) {
            ("1") -> {
                print("\nRegistrar Un Nuevo Celular\n\nIngrese los datos solicitados.\n")

                print("Modelo: ")
                val modelo = lectorDatos.nextLine()

                print("Sistema Operativo: ")
                val sistemaOperativo = lectorDatos.nextLine()

                print("Almacenamiento en GB: ")
                val almacenamiento: Int = lectorDatos.nextLine().toInt()

                print("Precio: ")
                val precio: Double = lectorDatos.nextLine().toDouble()

                print("¿Es Gamer? (1 = SI, 0 = NO): ")
                val opcionGamer = lectorDatos.nextLine().toInt()
                var esGamer: Boolean = opcionGamer == 1

                controladorCelular.manejadorCrearCelular(nombreMarca, modelo, sistemaOperativo,
                    almacenamiento, precio, esGamer)

                celulares()
            }
            ("2") -> {
                var opcionesVerCelular: String = ""
                opcionesVerCelular += "\nSeleccione una opcion:\n"
                opcionesVerCelular += "1. Visualizar un modelo de Celular especifico\n"
                opcionesVerCelular += "2. Visualizar todos los modelos de Celular\n"
                opcionesVerCelular += "3. Regresar\n"
                println(opcionesVerCelular)

                print("-> ")
                val opcionVisualizar = lectorDatos.nextLine()
                when (opcionVisualizar) {
                    ("1") -> {
                        print("\nIngrese el modelo del Celular: ")
                        val celularVisualizar: String = lectorDatos.nextLine()

                        controladorCelular.manejadorVisualizarCelular(1, nombreMarca, celularVisualizar)
                    }
                    ("2") -> {
                        controladorCelular.manejadorVisualizarCelular(2, nombreMarca, "")
                    }
                    else -> {
                        celulares()
                    }
                }
            }
            ("3") -> {
                print("\nActualizar Datos De Un Celular.\n\nIngrese el modelo del Celular a actualizar.\n")

                print("Modelo: ")
                val modelo = lectorDatos.nextLine()

                if (Marca().getByName(nombreMarca)?.listaCelulares != null) {
                    print("Ingrese los nuevos datos para el Celular:\n")

                    print("Modelo: ")
                    val modeloAct = lectorDatos.nextLine()

                    print("Sistema Operativo: ")
                    val soAct = lectorDatos.nextLine()

                    print("Almacenamiento en GB: ")
                    val almacenamientoAct: Int = lectorDatos.nextLine().toInt()

                    print("Precio: ")
                    val precioAct: Double = lectorDatos.nextLine().toDouble()

                    print("¿Es Gamer? (1 = SI, 0 = NO): ")
                    val opcionGamerAct = lectorDatos.nextLine().toInt()
                    var esGamerAct: Boolean = opcionGamerAct == 1

                    controladorCelular.manejadorActualizarCelular(nombreMarca, modelo, modeloAct, soAct,
                        almacenamientoAct, precioAct, esGamerAct)

                    celulares()
                } else {
                    println("La Marca $nombreMarca No Tiene Celulares Resgistrados.")
                    celulares()
                }
            }
            ("4") -> {
                println("\nEliminar Un Celular\n")
                print("Ingrese el modelo del Celular a eliminar.\n")

                print("Modelo: ")
                val modelo = lectorDatos.nextLine()

                controladorCelular.manejadorEliminarCelular(nombreMarca, modelo)
                celulares()
            }
            else -> {
                gestionarMYC()
            }
        }
    } else {
        println("La Marca $nombreMarca No Existe")
        gestionarMYC()
    }
}

fun visualizarModelos() {
    Marca().mostrarListaMarcaYCelulares()
    main()
}

fun leerMarcas(): ArrayList<ArrayList<String>> {
    val archivoM = File("marcas.txt")
    val contenidoM: ArrayList<String> = arrayListOf<String>()
    val marcas: ArrayList<ArrayList<String>> = arrayListOf<ArrayList<String>>()

    try {
        val lectorM = BufferedReader(archivoM.reader())

        lectorM.useLines { lines ->
            lines.forEach {
                if (it != "st") { // Verificar si hay marcas
                    if (it != "-") { // Se obtienen los datos de una Marca
                        contenidoM.add(it)
                    } else { // Se guardan los datos de una Marca en un arreglo general
                        marcas.add(ArrayList(contenidoM))
                        contenidoM.clear()
                    }
                } else {
                    contenidoM.add("SD")
                    marcas.add(contenidoM)
                    return marcas
                }
            }
        }
        lectorM.close()
        archivoM.delete()
        return marcas
    } catch (e: Exception) {
        throw RuntimeException("Error al leer el archivo", e)
    }
}

fun leerCelulares(): ArrayList<MutableList<Celular>> {
    val archivoC = File("celulares.txt")
    val contenidoC: ArrayList<String> = arrayListOf<String>()
    val celularesPorMarca: ArrayList<MutableList<Celular>> = arrayListOf()

    try {
        val lectorC = BufferedReader(archivoC.reader())
        val listaMutableCelulares: MutableList<Celular> = mutableListOf()

        lectorC.useLines { lines ->
            lines.forEach {
                if (it != "st") { // Verificar si hay Celulares de una Marca
                    if (it != "-" && it != "--") { // Se obtienen los datos de un Celular de una misma Marca
                        contenidoC.add(it)
                    } else if (it == "--") {
                        celularesPorMarca.add(ArrayList(listaMutableCelulares))
                        listaMutableCelulares.clear()
                    } else {
                        val celularRecuperado: Celular = Celular(contenidoC[0], contenidoC[1],
                            contenidoC[2].toInt(), contenidoC[3].toDouble(), contenidoC[4].toBooleanStrict())
                        listaMutableCelulares.add(celularRecuperado)
                        contenidoC.clear()
                    }
                } else {
                    listaMutableCelulares.add(Celular())
                    celularesPorMarca.add(ArrayList(listaMutableCelulares))
                    listaMutableCelulares.clear()
                }
            }
        }
        archivoC.delete()
        lectorC.close()
    } catch (e: Exception) {
        throw RuntimeException("Error al leer el archivo", e)
    }
    return celularesPorMarca
}

fun recuperarMarcasYCelulares(): MutableList<Marca> {
    val listaMarcas: MutableList<Marca> = mutableListOf<Marca>()
    val marcasObtenidas: ArrayList<ArrayList<String>> = leerMarcas()
    val celularesObtenidos: ArrayList<MutableList<Celular>> = leerCelulares()

    if (marcasObtenidas.isNotEmpty()) {
        val formatoFecha = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
        var fechaFund: String = ""
        var fechaFundacionR: Date = Date()
        var marcaRecuperada: Marca = Marca()

        for (i in 0 until marcasObtenidas.size){
            fechaFund = marcasObtenidas[i][1]

            try {
                fechaFundacionR = formatoFecha.parse(fechaFund)
            } catch (e: Exception) {
                println("Error al convertir la cadena a fecha: ${e.message}")
            }

            marcaRecuperada = Marca(marcasObtenidas[i][0], fechaFundacionR, marcasObtenidas[i][2].toInt(),
                                marcasObtenidas[i][3].toDouble(), celularesObtenidos[i])

            listaMarcas.add(marcaRecuperada)
        }
    }

    return listaMarcas
}

fun guardarMarcasYCelulares() {
    val datosMarcas: String = Marca().prepararMarcasGuardar()
    val datosCelulares: String = Marca().prepararCelularesGuardar()
    val archivoM = File("marcas.txt")
    val archivoC = File("celulares.txt")

    try {
        val escritorM = FileWriter(archivoM, false)
        escritorM.write(datosMarcas)
        escritorM.flush()
        escritorM.close()
        val escritorC = FileWriter(archivoC, false)
        escritorC.write(datosCelulares)
        escritorC.flush()
        escritorC.close()

        println("Archivos creados con éxito.")
    } catch (e: Exception) {
        println("Error al crear los archivos: $e")
    }
}