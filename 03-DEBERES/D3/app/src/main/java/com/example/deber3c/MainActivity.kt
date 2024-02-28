package com.example.deber3c

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val inicio: Inicio = Inicio()
    val actividad: Actividad = Actividad()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val navigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    loadFragmentos(inicio)
                    true
                }
                R.id.actividad -> {
                    loadFragmentos(actividad)
                    true
                }
                else -> false
            }
        }
        loadFragmentos(inicio)
    }

    fun loadFragmentos(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}