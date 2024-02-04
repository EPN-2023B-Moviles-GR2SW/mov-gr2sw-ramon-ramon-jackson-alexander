package com.example.deber02.bd

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.modelo.Marca
import java.sql.SQLException

class SqliteHelperMarca (
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "db_marca_celulares", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaMarca =
            """
                CREATE TABLE IF NOT EXISTS MARCA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                fecha_fundacion VARCHAR(10),
                cantidad_modelos INTEGER,
                ingresos_anuales REAL
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaMarca)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearMarca(
        nombre: String,
        fecha_fundacion: String,
        cantidad_modelos: Int,
        ingresos_anuales: Double
    ): Long {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombre)
            put("fecha_fundacion", fecha_fundacion)
            put("cantidad_modelos", cantidad_modelos)
            put("ingresos_anuales", ingresos_anuales)
        }

        return try {
            basedatosEscritura.insert("MARCA", null, valoresAGuardar)
        } catch (e: SQLException) {
            -1
        } finally {
            basedatosEscritura.close()
        }
    }

    fun eliminarMarca(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val resultadoEliminacion = baseDatosEscritura.delete("MARCA", "id = ?", arrayOf(id.toString()))
        baseDatosEscritura.close()

        return resultadoEliminacion != -1
    }

    fun actualizarMarca(
        nombre: String,
        fecha_fundacion: String,
        cantidad_modelos: Int,
        ingresos_anuales: Double,
        id: Int
    ): Boolean {
        val conexionEcritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nombre)
            put("fecha_fundacion", fecha_fundacion)
            put("cantidad_modelos", cantidad_modelos)
            put("ingresos_anuales", ingresos_anuales)
        }

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEcritura.update(
            "MARCA",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )

        conexionEcritura.close()
        return resultadoActualizacion != -1
    }

    @SuppressLint("Range")
    fun obtenerTodosMarcas(): List<Marca> {
        val marcas = mutableListOf<Marca>()
        val baseDatosLectura = readableDatabase
        val cursor: Cursor?

        try {
            cursor = baseDatosLectura.rawQuery("SELECT * FROM MARCA", null)
        } catch (e: SQLException) {
            return emptyList()
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val fecha_fundacion = cursor.getString(cursor.getColumnIndex("fecha_fundacion"))
                val cantidad_modelos = cursor.getInt(cursor.getColumnIndex("cantidad_modelos"))
                val ingresos_anuales = cursor.getDouble(cursor.getColumnIndex("ingresos_anuales"))

                marcas.add(Marca(id, nombre, fecha_fundacion, cantidad_modelos, ingresos_anuales, ArrayList()))
            } while (cursor.moveToNext())
        }

        cursor?.close()
        baseDatosLectura.close()

        return marcas
    }
}