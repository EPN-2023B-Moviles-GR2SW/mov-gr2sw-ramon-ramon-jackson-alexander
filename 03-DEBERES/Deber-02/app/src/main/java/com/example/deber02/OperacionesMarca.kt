package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber02.modelo.Celular
import com.example.deber02.modelo.Marca

class OperacionesMarca : AppCompatActivity() {
    val arreglo = BaseDatosMemoria.arregloMarca
    var posicionItemSeleccionado = -1
    var nombre: String = ""
    var fechaFundacion: String = ""
    var cantidadModelos: String = ""
    var ingresosAnuales: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operaciones_marca)
        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        if (posicionItemSeleccionado != -1) {
            val inputNombre = findViewById<EditText>(R.id.input_nombre)
            val inputFechaFundacion = findViewById<EditText>(R.id.input_fecha_fundacion)
            val inputCantidadModelos = findViewById<EditText>(R.id.input_cantidad_modelos)
            val inputIngresosAnuales = findViewById<EditText>(R.id.input_ingresos_anuales)

            inputNombre.setText(arreglo[posicionItemSeleccionado].nombre)
            inputFechaFundacion.setText(arreglo[posicionItemSeleccionado].fechaFundacion)
            inputCantidadModelos.setText(arreglo[posicionItemSeleccionado].cantidadModelos.toString())
            inputIngresosAnuales.setText(arreglo[posicionItemSeleccionado].ingresosAnuales.toString())
        }

        val botonCrear = findViewById<Button>(R.id.btn_guardar_m)
        if (posicionItemSeleccionado == -1) {
            botonCrear.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                fechaFundacion = findViewById<EditText>(R.id.input_fecha_fundacion).text.toString()
                cantidadModelos = findViewById<EditText>(R.id.input_cantidad_modelos).text.toString()
                ingresosAnuales = findViewById<EditText>(R.id.input_ingresos_anuales).text.toString()

                val listaCelulares: ArrayList<Celular> = arrayListOf()

                arreglo.add(
                    Marca(-1, nombre, fechaFundacion, cantidadModelos.toInt(), ingresosAnuales.toDouble(), listaCelulares)
                )

                devolverRespuesta()
            }
        }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_m)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                fechaFundacion = findViewById<EditText>(R.id.input_fecha_fundacion).text.toString()
                cantidadModelos = findViewById<EditText>(R.id.input_cantidad_modelos).text.toString()
                ingresosAnuales = findViewById<EditText>(R.id.input_ingresos_anuales).text.toString()

                arreglo[posicionItemSeleccionado].nombre = nombre
                arreglo[posicionItemSeleccionado].fechaFundacion = fechaFundacion
                arreglo[posicionItemSeleccionado].cantidadModelos = cantidadModelos.toInt()
                arreglo[posicionItemSeleccionado].ingresosAnuales = ingresosAnuales.toDouble()

                // mandar el nombre de la marca usando el "itemSeleccionado" para que el metodo
                // de actualizar en BD se pueda hacer !!!

                devolverRespuesta()
            }
        }

    }

    private fun devolverRespuesta() {
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("posicion", posicionItemSeleccionado)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}