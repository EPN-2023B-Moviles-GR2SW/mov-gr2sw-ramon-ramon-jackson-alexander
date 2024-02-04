package com.example.deber02.bd

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.modelo.Celular
import java.sql.SQLException

class SQLiteHelperCelular (
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "db_marca_celulares", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCelular =
            """
                CREATE TABLE IF NOT EXISTS CELULAR (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                modelo VARCHAR(50),
                sistema_operativo VARCHAR(10),
                almacenamiento_gb INTEGER,
                precio REAL,
                es_gamer BOOLEAN,
                id_marca INTEGER,
                FOREIGN KEY (id_marca) REFERENCES MARCA(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCelular)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearCelular(
        modelo: String,
        sistema_operativo: String,
        almacenamiento_gb: Int,
        precio: Double,
        es_gamer: Boolean,
        id_marca: Int
    ): Long {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("modelo", modelo)
            put("sistema_operativo", sistema_operativo)
            put("almacenamiento_gb", almacenamiento_gb)
            put("precio", precio)
            put("es_gamer", es_gamer)
            put("id_marca", id_marca)
        }

        return try {
            basedatosEscritura.insert("CELULAR", null, valoresAGuardar)
        } catch (e: SQLException) {
            -1
        } finally {
            basedatosEscritura.close()
        }
    }

    fun eliminarCelular(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val resultadoEliminacion = baseDatosEscritura.delete("CELULAR", "id = ?", arrayOf(id.toString()))
        baseDatosEscritura.close()

        return resultadoEliminacion != -1
    }

    fun actualizarCelular(
        id: Int,
        modelo: String,
        sistema_operativo: String,
        almacenamiento_gb: Int,
        precio: Double,
        es_gamer: Boolean,
        id_marca: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("modelo", modelo)
            put("sistema_operativo", sistema_operativo)
            put("almacenamiento_gb", almacenamiento_gb)
            put("precio", precio)
            put("es_gamer", es_gamer)
            put("id_marca", id_marca)
        }

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "CELULAR",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )

        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    @SuppressLint("Range")
    fun obtenerTodosCelulares(idMarca: Int): List<Celular> {
        val carros = mutableListOf<Celular>()
        val baseDatosLectura = readableDatabase
        val cursor: Cursor?

        try {
            cursor = baseDatosLectura.rawQuery("SELECT * FROM CELULAR WHERE idMarca = ?", arrayOf(idMarca.toString()))
        } catch (e: SQLException) {
            return emptyList()
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                val sistema_operativo = cursor.getString(cursor.getColumnIndex("sistema_operativo"))
                val almacenamiento_gb = cursor.getInt(cursor.getColumnIndex("almacenamiento_gb"))
                val precio = cursor.getDouble(cursor.getColumnIndex("precio"))
                val es_gamer = cursor.getInt(cursor.getColumnIndex("es_gamer")) == 1

                carros.add(Celular(id, sistema_operativo, modelo, almacenamiento_gb, precio, es_gamer, idMarca))
            } while (cursor.moveToNext())
        }

        cursor?.close()
        baseDatosLectura.close()

        return carros
    }
}