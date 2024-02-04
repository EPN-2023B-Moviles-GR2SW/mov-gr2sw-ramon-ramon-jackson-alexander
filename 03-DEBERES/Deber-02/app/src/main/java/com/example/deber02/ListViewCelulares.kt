package com.example.deber02

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.deber02.bd.BaseDeDatos
import com.example.deber02.bd.SQLiteHelperCelular
import com.example.deber02.modelo.Celular
import com.google.android.material.snackbar.Snackbar

class ListViewCelulares : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Celular>
    private val dbHelperCelular = SQLiteHelperCelular(this)
    var posicionArreglo = 0
    var posicionItemSeleccionado = 0
    var listaCelular = mutableListOf<Celular>()

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                if (result.data != null) {
                    adaptador.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_celulares)

        BaseDeDatos.tablaCelular = SQLiteHelperCelular(this)

        posicionArreglo = intent.getIntExtra("posicion", -1)

        val txtMarca = findViewById<TextView>(R.id.textView_nombre_m)
        txtMarca.text = "Marca: ${BaseDeDatos.tablaMarca?.obtenerTodasMarcas()?.get(posicionArreglo)?.nombre}"

        listaCelular = dbHelperCelular.obtenerTodosCelulares(BaseDeDatos.tablaMarca?.obtenerTodasMarcas()?.get(posicionArreglo)?.idMarca ?: -1)
            .toMutableList()
        val listView = findViewById<ListView>(R.id.lv_list_celulares)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaCelular
        )
        listView.adapter = adaptador

        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_lc)
        botonAnadirListView.setOnClickListener {
            abrirActividadConParametros(OperacionesCelulares::class.java)
        }

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_celulares, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        adaptador.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
                abrirActividadConParametros(OperacionesCelulares::class.java)
                return true
            }
            R.id.mi_eliminar_c -> {
                dbHelperCelular.eliminarCelular(
                    dbHelperCelular.obtenerTodosCelulares(
                        BaseDeDatos.tablaMarca?.obtenerTodasMarcas()?.get(posicionArreglo)?.idMarca ?: -1
                    )[posicionItemSeleccionado].idCelular
                )
                mostrarSnackbar("Celular eliminado")
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(findViewById(R.id.lv_list_celulares),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicion", posicionItemSeleccionado)
        intentExplicito.putExtra("posicionArreglo", posicionArreglo)

        callbackContenido.launch(intentExplicito)
    }
}