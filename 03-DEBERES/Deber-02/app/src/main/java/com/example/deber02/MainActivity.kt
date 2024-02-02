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
import com.example.deber02.modelo.Marca
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    val arreglo = BaseDatosMemoria.arregloMarca
    var posicionItemSeleccionado = 0
    lateinit var adaptador: ArrayAdapter<Marca>

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                if (result.data != null) {
                    // logica negocio
                    val data = result.data
                    adaptador.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_marcas)

        val listView = findViewById<ListView>(R.id.lv_list_view_marcas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_marca)
        botonAnadirListView.setOnClickListener {
            posicionItemSeleccionado = -1
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
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_m -> {
                abrirActividadConParametros(OperacionesMarca::class.java)
                return true
            }

            R.id.mi_eliminar_m -> {
                mostrarSnackbar("Marca ${arreglo[posicionItemSeleccionado].nombre} eliminada.")
                // Eliminar completamente
                arreglo.removeAt(posicionItemSeleccionado)
                //arreglo[posicionItemSeleccionado].isOpen = false
                adaptador.notifyDataSetChanged()
                return true
            }

            R.id.mi_ver_celulares -> {
                abrirActividadConParametros(ListViewCelulares::class.java)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_list_view_marcas),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicion", posicionItemSeleccionado)

        callbackContenido.launch(intentExplicito)
    }
}