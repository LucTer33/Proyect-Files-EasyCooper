package com.pmdm.tienda.models


data class Direccion(
    val calle: String?,
    val ciudad: String?,
    val codigoPostal: String?)
{
    override fun toString(): String {
        return "$calle  $ciudad ($codigoPostal)"
    }
}

data class Cliente(
    val dni: String="",
    val correo: String="",
    val nombre: String="",
    val telefono: String="",
    val direccion: Direccion?=null,
    val favoritos: MutableList<Int> = mutableListOf()
)

