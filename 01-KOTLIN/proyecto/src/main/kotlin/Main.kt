import java.util.*

fun main() {
    println("Hello World!")
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Jackson";

    // Mutables (Re asignar)
    var mutable: String = "Alexander";
    mutable = "Json";

    // val > var
    // Duck typing
    var ejemploVariable = "Jackson Ramon"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombrProfesor = "Adrian Eguez";
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'C';
    val mayorEdad: Boolean = true;
    // Clases Java
    val fechaNacimiento: Date = Date();

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) // Parametros nombrados

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
}

// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    // "Nombre : " + nombre
    println("Nombre: ${nombre}") // template strings
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (pero tiene valores por defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable (puede tener el valor de nulo, va a ser Double o nulo)
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){ // Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor primario
    // Ejemplo:
    // uno: Int, (Parametro (sin modificar de acceso))
    // private var una: Int, // Propiedad Publica Clase numeros.uno
    // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,
    protected val numeroUno: Int, // Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, // Propiedad de la clase protected numeros.numeroDos
) {
    // var cedula: String = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)

    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}

class Suma( // Constructor Primario Suma
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametro
): Numeros(unoParametro, dosParametro) { // Extendiendo y mandando los parametros (super)
    init { // Bloque codigo constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( // Segundo constructor
        uno: Int?, // Parametros
        dos: Int // Parametros
    ):this (
        if (uno == null) 0 else uno,
        dos
    )

    constructor( // Tercer constructor
        uno: Int, // Parametros
        dos: Int? // Parametros
    ):this (
        uno,
        if (dos == null) 0 else dos,
    )
}

