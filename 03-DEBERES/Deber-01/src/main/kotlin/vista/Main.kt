package vista

import controlador.MarcaCelularController
import java.text.SimpleDateFormat
import java.util.*

fun main(){
    val lectorDatos = Scanner(System.`in`)

    var bienvenida: String = ""
    bienvenida += "Bienvenido al Sistema de Gestion de Marcas de Celulares\n\n"
    bienvenida += "Seleccione una opcion para iniciar:\n"
    bienvenida += "1. Gestionar Marcas y Celulares\n"
    bienvenida += "2. Visualizar Modelos de Celulares\n"
    bienvenida += "3. Salir\n"
    println(bienvenida)

    print("Ingrese la opcion que desea seleccionar: ")
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
    print("Ingrese la opcion que desea seleccionar: ")
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
    opcionesMarcasCelular += "2. Visualizar los datos de una Marca\n"
    opcionesMarcasCelular += "3. Actualizar los datos de una Marca\n"
    opcionesMarcasCelular += "4. Eliminar una Marca\n"
    opcionesMarcasCelular += "5. Regresar\n"
    println(opcionesMarcasCelular)

    val lectorDatos = Scanner(System.`in`)
    print("Ingrese la opcion que desea seleccionar: ")
    val opcionMenu = lectorDatos.nextLine()
    val controladorMarca: MarcaCelularController = MarcaCelularController()
    when (opcionMenu) {
        ("1") -> {
            print("\nRegistrar una nueva Marca.\n\nIngrese los datos solicitados.\n")

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

            // MANDAR ESTOS DATOS AL CONTROLADOR
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

            print("Ingrese la opcion que desea seleccionar: ")
            val opcionVisualizar = lectorDatos.nextLine()
            when (opcionVisualizar) {
                ("1") -> {
                    print("\nIngrese el nombre de la Marca: ")
                    val marcaVisualizar: String = lectorDatos.nextLine()

                    // Llamar al controlador
                    controladorMarca.visualizarMarca(1, marcaVisualizar)
                }
                ("2") -> {
                    controladorMarca.visualizarMarca(2, "")
                }
                else -> {
                    marcasCelular()
                }
            }
        }
        ("3") -> {
            //
        }
        else -> {
            gestionarMYC()
        }
    }
}

fun celulares() {

}

fun visualizarModelos() {

}