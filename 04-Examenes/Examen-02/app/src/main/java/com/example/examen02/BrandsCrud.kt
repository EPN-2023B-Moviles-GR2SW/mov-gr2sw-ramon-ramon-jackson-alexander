package com.example.examen02


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examen02.model.Marca
import com.example.examen02.model.Celular
import com.google.firebase.firestore.FirebaseFirestore

class BrandsCrud : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val brandsCollection = db.collection("cell_phone_brands")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands_crud)  // cambiar nombre

        val selectedIndexItem = intent.getIntExtra("position", -1)
        val brandId = intent.getStringExtra("brandId")
        val brandName = intent.getStringExtra("brandName")
        val brandAddress = intent.getStringExtra("brandAddress")
        val brandPhone = intent.getStringExtra("brandPhone")
        val brandEmail = intent.getStringExtra("brandEmail")


        findViewById<EditText>(R.id.input_id_d).setText(brandId)
        findViewById<EditText>(R.id.input_name_d).setText(brandName)
        findViewById<EditText>(R.id.input_address_d).setText(brandAddress)
        findViewById<EditText>(R.id.input_phone_d).setText(brandPhone)
        findViewById<EditText>(R.id.input_email_d).setText(brandEmail)

        val saveButton = findViewById<Button>(R.id.btn_save_distributor)
        saveButton.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_d).text.toString()
            val name = findViewById<EditText>(R.id.input_name_d).text.toString()
            val address = findViewById<EditText>(R.id.input_address_d).text.toString()
            val phone = findViewById<EditText>(R.id.input_phone_d).text.toString()
            val email = findViewById<EditText>(R.id.input_email_d).text.toString()
            val cellphoneList = mutableListOf<Celular>()

            val brand = Marca(id, name, address, phone, email, cellphoneList)

            if (selectedIndexItem == -1) {
                brandsCollection.add(brand)
                    .addOnSuccessListener {
                        response(-1)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar una nueva marca", e)
                    }
            } else {
                val nameF = intent.getStringExtra("nameF")
                brandsCollection.document(nameF!!)
                    .set(brand)
                    .addOnSuccessListener {
                        response(selectedIndexItem)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al actualizar la marca", e)
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
}
