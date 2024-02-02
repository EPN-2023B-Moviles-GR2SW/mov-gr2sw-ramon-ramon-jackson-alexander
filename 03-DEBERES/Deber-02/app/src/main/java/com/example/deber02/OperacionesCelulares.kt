package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber02.modelo.Celular

class OperacionesCelulares : AppCompatActivity() {
    val arreglo = BaseDatosMemoria.arregloMarca
    var listaCelular = arrayListOf<Celular>()
    var posicionArreglo = -1
    var posicionItemSeleccionado = -1
    var modelo: String = ""
    var sistemaOperativo: String = ""
    var almacenamientoGB: String = ""
    var precio: String = ""
    var esGamer: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operaciones_celulares)

        posicionArreglo = intent.getIntExtra("posicionArreglo", -1)
        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        listaCelular = arreglo[posicionArreglo].listaCelulares

        if (posicionItemSeleccionado != -1) {
            val inputModelo = findViewById<EditText>(R.id.input_modelo)
            val inputSistemaOperativo = findViewById<EditText>(R.id.input_so)
            val inputAlmacenamiento = findViewById<EditText>(R.id.input_almacenamiento)
            val inputPrecio = findViewById<EditText>(R.id.input_precio)
            val inputEsGamer = findViewById<EditText>(R.id.input_gamer)

            inputModelo.setText(listaCelular[posicionItemSeleccionado].modelo)
            inputSistemaOperativo.setText(listaCelular[posicionItemSeleccionado].sistemaOperativo)
            inputAlmacenamiento.setText(listaCelular[posicionItemSeleccionado].almacenamientoGB.toString())
            inputPrecio.setText(listaCelular[posicionItemSeleccionado].precio.toString())
            inputEsGamer.setText(listaCelular[posicionItemSeleccionado].esGamer.toString())

        }

        val botonCrear = findViewById<Button>(R.id.btn_guardar_c)
        if (posicionItemSeleccionado == -1) {
            botonCrear.setOnClickListener {
                modelo = findViewById<EditText>(R.id.input_modelo).text.toString()
                sistemaOperativo = findViewById<EditText>(R.id.input_so).text.toString()
                almacenamientoGB = findViewById<EditText>(R.id.input_almacenamiento).text.toString()
                precio = findViewById<EditText>(R.id.input_precio).text.toString()
                esGamer = findViewById<EditText>(R.id.input_gamer).text.toString()

                listaCelular.add(
                    Celular(modelo, sistemaOperativo, almacenamientoGB.toInt(), precio.toDouble(),
                        (esGamer.uppercase() == "SI"))
                )

                devolverRespuesta()
            }
        }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_c)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                modelo = findViewById<EditText>(R.id.input_modelo).text.toString()
                almacenamientoGB = findViewById<EditText>(R.id.input_almacenamiento).text.toString()
                precio = findViewById<EditText>(R.id.input_precio).text.toString()
                esGamer = findViewById<EditText>(R.id.input_gamer).text.toString()

                listaCelular[posicionItemSeleccionado].modelo = modelo
                listaCelular[posicionItemSeleccionado].almacenamientoGB = almacenamientoGB.toInt()
                listaCelular[posicionItemSeleccionado].precio = precio.toDouble()
                listaCelular[posicionItemSeleccionado].esGamer = (esGamer.uppercase() == "SI")

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