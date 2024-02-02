package com.example.deber02

import com.example.deber02.modelo.Celular
import com.example.deber02.modelo.Marca

class BaseDatosMemoria {
    companion object {
        val arregloMarca = arrayListOf<Marca>()
        val listaCelulares1: ArrayList<Celular> = arrayListOf()
        val listaCelulares2: ArrayList<Celular> = arrayListOf()
        val listaCelulares3: ArrayList<Celular> = arrayListOf()
        init {

            listaCelulares1.add(Celular("Redmi note 10", "Android", 64, 180.0, false))
            listaCelulares1.add(Celular("Redmi note 10 pro", "Android", 128, 259.99, false))

            listaCelulares2.add(Celular("Galaxy s9 plus", "Android", 64, 210.0, false))
            listaCelulares2.add(Celular("Galaxy s23", "Android", 256, 849.99, true))

            listaCelulares3.add(Celular("IPhone 12", "IOS", 128, 310.0, true))
            listaCelulares3.add(Celular("IPhone 13", "IOS", 128, 540.0, true))

            arregloMarca.add(Marca("Xiaomi", "08/10/2000", 120, 1237890.48, listaCelulares1))
            arregloMarca.add(Marca("Samsung", "02/10/2000", 200, 98745600.15, listaCelulares2))
            arregloMarca.add(Marca("IOS", "09/04/1992", 84, 96325800.20, listaCelulares3))
        }
    }
}