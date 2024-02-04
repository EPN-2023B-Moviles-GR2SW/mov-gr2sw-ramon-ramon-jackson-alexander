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
import androidx.activity.result.contract.ActivityResultContracts
import com.example.deber02.bd.BaseDeDatos
import com.example.deber02.bd.SQLiteHelperCelular
import com.example.deber02.bd.SQLiteHelperMarca
import com.example.deber02.modelo.Marca
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Marca>
    private val dbHelperMarca = BaseDeDatos.tablaMarca

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                adaptador.clear()
                adaptador.addAll(dbHelperMarca?.obtenerTodasMarcas() ?: mutableListOf())
                adaptador.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_marcas)

        BaseDeDatos.tablaMarca = SQLiteHelperMarca(this)
        BaseDeDatos.tablaCelular = SQLiteHelperCelular(this)

        val listView = findViewById<ListView>(R.id.lv_list_view_marcas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            dbHelperMarca?.obtenerTodasMarcas() ?: mutableListOf()
        )
        listView.adapter = adaptador

        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_marca)
        botonAnadirListView.setOnClickListener {
            abrirActividadConParametros(OperacionesMarca::class.java)
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
        inflater.inflate(R.menu.menu_marcas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        adaptador.notifyDataSetChanged()
        mostrarSnackbar("Posición seleccionada: $posicion")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        when (item.itemId) {
            R.id.mi_editar_m -> {
                abrirActividadConParametros(OperacionesMarca::class.java, posicion)
                return true
            }

            R.id.mi_eliminar_m -> {
                dbHelperMarca?.eliminarMarca(
                    dbHelperMarca?.obtenerTodasMarcas()?.get(posicion)?.idMarca ?: 0
                )
                adaptador.clear()
                adaptador.addAll(dbHelperMarca?.obtenerTodasMarcas() ?: mutableListOf())
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Marca eliminada")
                return true
            }

            R.id.mi_ver_celulares -> {
                abrirActividadConParametros(ListViewCelulares::class.java)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_list_view_marcas),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>, posicion: Int? = null) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicion", posicion ?: -1)
        callbackContenido.launch(intentExplicito)
    }
}