package com.example.examen02.model

data class Marca(
    var brandId: String?,
    var name: String?,
    var address: String?,
    var phone: String?,
    var email: String?,
    var cellphoneList: MutableList<Celular>?
) {

    constructor() : this(null, null, null, null, null, null)

    override fun toString(): String {
        return "Nombre: ${name}\nDirección: ${address}\nTeléfono: ${phone}\nEmail: ${email}"
    }
}