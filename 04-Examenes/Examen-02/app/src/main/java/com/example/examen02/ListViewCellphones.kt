package com.example.examen02

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
import com.example.examen02.model.Marca
import com.example.examen02.model.Celular
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class ListViewCellphones : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val brandsCollection = db.collection("cell_phone_brands")

    private var nameF = ""
    private var indexSelectedItem = 0
    private var brandPosition = -1
    private var cellphoneList = mutableListOf<Celular>()
    private var brandId = ""
    private lateinit var adapter: ArrayAdapter<Celular>

    private val contentCallback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            updateCellphoneList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_cellphones)
        nameF = intent.getStringExtra("nameF").toString()

        val brandName = intent.getStringExtra("BrandName").toString()
        brandId = intent.getStringExtra("brandId").toString()
        brandPosition = intent.getIntExtra("BrandPosition", -1)
        val txtViewCellphones = findViewById<TextView>(R.id.txtV_products)
        if (brandName != null) {
            txtViewCellphones.text = brandName.uppercase()
        }

        val listView = findViewById<ListView>(R.id.listView_products)
        registerForContextMenu(listView)

        val createCellphoneButtonLv = findViewById<Button>(R.id.btn_create_product_lv)
        createCellphoneButtonLv.setOnClickListener {
            indexSelectedItem = -1
            openActivityWithParameters(CellphonesCrud::class.java)
        }
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            cellphoneList
        )
        listView.adapter = adapter
        updateCellphoneList()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        indexSelectedItem = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_update -> {
                openActivityWithParameters(CellphonesCrud::class.java)
                true
            }
            R.id.mi_delete -> {
                val deletedCellphone = cellphoneList.removeAt(indexSelectedItem)
                adapter.notifyDataSetChanged()
                deleteCellphoneFromFirestore(deletedCellphone)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteCellphoneFromFirestore(product: Celular) {
        brandsCollection.document(nameF)
            .update("cellphoneList", cellphoneList)
            .addOnSuccessListener {
                mostrarSnackbar("Celular eliminado con éxito")
                adapter.clear()
                adapter.addAll(cellphoneList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                mostrarSnackbar("Error al eliminar el celular: ${exception.message}")
                cellphoneList.add(indexSelectedItem, product)
                adapter.notifyDataSetChanged()
            }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_products), //cambiar nombre
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun openActivityWithParameters(clase: Class<*>) {
        val explicitIntent = Intent(this, clase)
        explicitIntent.putExtra("position", indexSelectedItem)
        explicitIntent.putExtra("BrandPosition", brandPosition)
        explicitIntent.putExtra("brandId", getBrandId())
        explicitIntent.putExtra("nameF", nameF)
        if (indexSelectedItem != -1) {
            val selectedCellphone = cellphoneList[indexSelectedItem]
            explicitIntent.putExtra("cellphoneId", selectedCellphone.id)
            explicitIntent.putExtra("cellphoneName", selectedCellphone.name)
            explicitIntent.putExtra("cellphonePrice", selectedCellphone.price)
            explicitIntent.putExtra("cellphoneStock", selectedCellphone.stock)
            explicitIntent.putExtra("cellphoneIsAvailable", selectedCellphone.isAvailable)
            explicitIntent.putExtra("editar", 0)
        }
        contentCallback.launch(explicitIntent)
    }

    private fun updateCellphoneList() {
        brandsCollection.document(nameF).get()
            .addOnSuccessListener { documentSnapshot ->
                val brand = documentSnapshot.toObject(Marca::class.java)
                if (brand != null) {
                    cellphoneList = brand.cellphoneList!!
                    adapter.clear()
                    adapter.addAll(cellphoneList)
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun getBrandId(): String {
        return brandId
    }
}