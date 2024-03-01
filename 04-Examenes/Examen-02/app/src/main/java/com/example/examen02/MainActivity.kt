package com.example.examen02

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examen02.model.Marca
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val brandsCollection = db.collection("cell_phone_brands")
    private val documentNames = mutableListOf<String>()

    private var indexSelectedItem = 0
    private var brandList = mutableListOf<Marca>()
    private lateinit var adapter: ArrayAdapter<Marca>

    private val callbackContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode === Activity.RESULT_OK) {
            if (result.data != null) {
                val data = result.data
                val position = data?.getIntExtra("position", -1)
                if (position != null && position != -1) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_brands) // Cambiar nombre

        val listView = findViewById<ListView>(R.id.listView_distributors) // Cambiar nombre
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            brandList
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        loadBrands()

        brandsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val documentId = document.id
                    documentNames.add(documentId)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener los documentos", exception)
            }

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedBrand = brandList[position]
            val explicitIntent = Intent(this, ListViewCellphones::class.java)
            explicitIntent.putExtra("brandId", selectedBrand.brandId)
            explicitIntent.putExtra("BrandName", selectedBrand.name)
            explicitIntent.putExtra("BrandPosition", position)
            explicitIntent.putExtra("nameF", documentNames[indexSelectedItem])
            callbackContent.launch(explicitIntent)
        }

        val createBrandButtonLv = findViewById<Button>(R.id.btn_create_distributor_lv) //Cambiar nombre
        createBrandButtonLv.setOnClickListener {
            indexSelectedItem = -1
            openActivityWithParameters(BrandsCrud::class.java)
        }
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        loadBrands()
    }

    private fun loadBrands() {
        brandsCollection.get()
            .addOnSuccessListener { result ->
                brandList.clear()
                for (document in result) {
                    val brand = document.toObject(Marca::class.java)
                    brandList.add(brand)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }
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
                openActivityWithParameters(BrandsCrud::class.java)
                true
            }
            R.id.mi_delete -> {
                val deletedBrand = brandList[indexSelectedItem]
                deleteBrandFromFirestore(deletedBrand)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    private fun deleteBrandFromFirestore(brand: Marca) {
        val brandId = brand.brandId
        if (brandId != null) {
            brandsCollection.document(documentNames[indexSelectedItem])
                .delete()
                .addOnSuccessListener {
                    brandList.remove(brand)
                    adapter.notifyDataSetChanged()
                    showSnackbar("Marca eliminada correctamente")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error al eliminar la marca", e)
                    showSnackbar("Error al eliminar la marca")
                }
        } else {
            Log.e(TAG, "ID de marca nulo")
            showSnackbar("ID de marca nulo")
        }
    }


    fun showSnackbar(text: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_distributors), // Cambiar nombre
            text,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun openActivityWithParameters(clase: Class<*>) {
        val explicitIntent = Intent(this, clase)
        explicitIntent.putExtra("position", indexSelectedItem)
        if (indexSelectedItem != -1) {
            val selectedBrand = brandList[indexSelectedItem]
            explicitIntent.putExtra("brandId", selectedBrand.brandId)
            explicitIntent.putExtra("brandName", selectedBrand.name)
            explicitIntent.putExtra("brandAddress", selectedBrand.address)
            explicitIntent.putExtra("brandPhone", selectedBrand.phone)
            explicitIntent.putExtra("brandEmail", selectedBrand.email)
            explicitIntent.putExtra("nameF", documentNames[indexSelectedItem])
        }
        callbackContent.launch(explicitIntent)
    }
}