package com.example.b2023gr2sw

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Jackson", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Alexander", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Abigail", "c@c.com")
                )
        }
    }

}