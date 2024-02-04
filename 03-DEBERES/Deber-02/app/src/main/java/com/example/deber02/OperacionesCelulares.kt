package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.deber02.bd.BaseDeDatos
import com.example.deber02.modelo.Celular

class OperacionesCelulares : AppCompatActivity() {
    var listaCelular = arrayListOf<Celular>()
    var posicionArreglo = -1
    var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operaciones_celulares)

        posicionArreglo = intent.getIntExtra("posicionArreglo", -1)
        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        // Obtener las instancias de la db desde BaseDeDatos
        val dbHelperMarca = BaseDeDatos.tablaMarca
        val dbHelperCelular = BaseDeDatos.tablaCelular

        // Obtiene datos de la base de datos en lugar de la memoria
        listaCelular = (dbHelperCelular?.obtenerTodosCelulares(posicionArreglo) ?: ArrayList()) as ArrayList<Celular>

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
                // Obtiene los datos desde las vistas
                val modelo = findViewById<EditText>(R.id.input_modelo).text.toString()
                val sistemaOperativo = findViewById<EditText>(R.id.input_so).text.toString()
                val almacenamiento = findViewById<EditText>(R.id.input_almacenamiento).text.toString().toInt()
                val precio = findViewById<EditText>(R.id.input_precio).text.toString().toDouble()
                val esGamer = findViewById<EditText>(R.id.input_gamer).text.toString()

                // Inserta el nuevo celular en la base de datos
                try {
                    dbHelperCelular?.crearCelular(modelo, sistemaOperativo, almacenamiento, precio,
                        (esGamer.uppercase() == "SI"), posicionArreglo)
                    // Devuelve la respuesta
                    devolverRespuesta()
                } catch (e: Exception) {
                    // Manejar errores de inserción
                    e.printStackTrace()
                }
            }
        }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_c)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                // Obtiene el nuevo precio desde la vista
                val nuevoPrecio = findViewById<EditText>(R.id.input_precio).text.toString()

                // Actualiza el precio en la base de datos
                try {
                    dbHelperCelular?.actualizarCelular(
                        listaCelular[posicionItemSeleccionado].idCelular,
                        listaCelular[posicionItemSeleccionado].modelo,
                        listaCelular[posicionItemSeleccionado].sistemaOperativo,
                        listaCelular[posicionItemSeleccionado].almacenamientoGB,
                        nuevoPrecio.toDouble(),
                        listaCelular[posicionItemSeleccionado].esGamer,
                        listaCelular[posicionArreglo].idMarca
                    )

                    devolverRespuesta()
                } catch (e: Exception) {
                    // Manejar errores de actualización
                    e.printStackTrace()
                }
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

    override fun onDestroy() {
        // Cierra la conexión a las bases de datos al destruir la actividad
        BaseDeDatos.tablaMarca?.close()
        BaseDeDatos.tablaCelular?.close()
        super.onDestroy()
    }
}