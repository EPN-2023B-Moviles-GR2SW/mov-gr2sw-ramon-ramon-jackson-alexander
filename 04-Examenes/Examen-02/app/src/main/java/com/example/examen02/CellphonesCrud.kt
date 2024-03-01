package com.example.examen02

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examen02.model.Marca
import com.example.examen02.model.Celular
import com.google.firebase.firestore.FirebaseFirestore

class CellphonesCrud : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val brandsCollection = db.collection("cell_phone_brands")
    private var nameF = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cellphones_crud) //cambiar nombre

        val selectedIndexItem = intent.getIntExtra("position", -1)
        val editar = intent.getIntExtra("editar", -1)
        val brandId = intent.getStringExtra("brandId")
        val cellphoneId = intent.getIntExtra("cellphoneId", -1)
        val cellphoneName = intent.getStringExtra("cellphoneName")
        val cellphonePrice = intent.getDoubleExtra("cellphonePrice", 0.0)
        val cellphoneStock = intent.getIntExtra("cellphoneStock", 0)
        nameF = intent.getStringExtra("nameF").toString()

        if( editar == 0) {
            findViewById<EditText>(R.id.input_id_p).setText(cellphoneId.toString())
            findViewById<EditText>(R.id.input_name_p).setText(cellphoneName)
            findViewById<EditText>(R.id.input_price_p).setText(cellphonePrice.toString())
            findViewById<EditText>(R.id.input_stock_p).setText(cellphoneStock.toString())
        }

        val saveButton = findViewById<Button>(R.id.btn_save_product)
        saveButton.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_p).text.toString().toInt()
            val name = findViewById<EditText>(R.id.input_name_p).text.toString()
            val price = findViewById<EditText>(R.id.input_price_p).text.toString().toDouble()
            val stock = findViewById<EditText>(R.id.input_stock_p).text.toString().toInt()
            val cellphoneList = mutableListOf<Celular>()

            val cellphone = Celular(id, name, price, stock, stock != 0, brandId)

            cellphoneList.add(cellphone)

            if (selectedIndexItem == -1) {
                Log.e(ContentValues.TAG, "Agregar un nuevo celular")
                brandsCollection.document(nameF)
                    .update("cellphoneList", cellphoneList)
                    .addOnSuccessListener {
                        response(-1)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar un nuevo celular", e)
                    }
            } else {
                // Actualizar el producto existente
                brandsCollection.document(nameF)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val brand = documentSnapshot.toObject(Marca::class.java)
                        brand?.let { brand ->
                            val updatedCellphoneList = brand.cellphoneList?.toMutableList()
                            val cellphoneIndex = updatedCellphoneList?.indexOfFirst { it.id == cellphone.id }
                            if (cellphoneIndex != null && cellphoneIndex != -1) {
                                updatedCellphoneList[cellphoneIndex] = cellphone
                                brandsCollection.document(nameF)
                                    .set(brand.copy(cellphoneList = updatedCellphoneList))
                                    .addOnSuccessListener {
                                        response(selectedIndexItem)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error al actualizar el celular", e)
                                    }
                            } else {
                                Log.e(TAG, "Celular no encontrado en la lista de la marca")
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al obtener la marca", e)
                    }
            }
        }
    }

    private fun response(position: Int) {
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("position", position)
        setResult(RESULT_OK, intentReturnParameters)
        finish()
    }

    companion object {
        private const val TAG = "CellphonesCrud"
    }
}