package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.deber02.bd.BaseDeDatos

class OperacionesMarca : AppCompatActivity() {
    var posicionItemSeleccionado = -1
    var nombre: String = ""
    var fechaFundacion: String = ""
    var cantidadModelos: String = ""
    var ingresosAnuales: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operaciones_marca)

        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        // Obtener la instancia de la db desde BaseDeDatos
        val dbHelperMarca = BaseDeDatos.tablaMarca

        if (posicionItemSeleccionado != -1) {
            val inputNombre = findViewById<EditText>(R.id.input_nombre)
            val inputFechaFundacion = findViewById<TextView>(R.id.input_fecha_fundacion)
            val inputCantidadModelos = findViewById<TextView>(R.id.input_cantidad_modelos)
            val inputIngresosAnuales = findViewById<TextView>(R.id.input_ingresos_anuales)

            inputNombre.setText(dbHelperMarca?.obtenerTodasMarcas()?.get(posicionItemSeleccionado)?.nombre)
            inputFechaFundacion.setText(dbHelperMarca?.obtenerTodasMarcas()?.get(posicionItemSeleccionado)?.fechaFundacion)
            inputCantidadModelos.setText(dbHelperMarca?.obtenerTodasMarcas()?.get(posicionItemSeleccionado)?.cantidadModelos.toString())
            inputIngresosAnuales.setText(dbHelperMarca?.obtenerTodasMarcas()?.get(posicionItemSeleccionado)?.ingresosAnuales.toString())
        }

        val botonCrear = findViewById<Button>(R.id.btn_guardar_m)
        if (posicionItemSeleccionado == -1) {
            botonCrear.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                fechaFundacion = findViewById<EditText>(R.id.input_fecha_fundacion).text.toString()
                cantidadModelos = findViewById<EditText>(R.id.input_cantidad_modelos).text.toString()
                ingresosAnuales = findViewById<EditText>(R.id.input_ingresos_anuales).text.toString()

                dbHelperMarca?.crearMarca(nombre, fechaFundacion, cantidadModelos.toInt(), ingresosAnuales.toDouble())

                dbHelperMarca?.close()

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

                dbHelperMarca?.actualizarMarca(
                    nombre,
                    fechaFundacion,
                    cantidadModelos.toInt(),
                    ingresosAnuales.toDouble(),
                    dbHelperMarca.obtenerTodasMarcas()?.get(posicionItemSeleccionado)?.idMarca ?: 0
                )

                dbHelperMarca?.close()

                devolverRespuesta()
            }
        }
    }

    private fun devolverRespuesta() {
        // Devuelve la posición del item modificado
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("posicion", posicionItemSeleccionado)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}