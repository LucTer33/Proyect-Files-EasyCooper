package com.pmdm.tienda.ui.features.newuser

data class DireccionClienteUiState(
    val calle: String?,
    val ciudad: String?,
    val codigoPostal: String?)
{
    override fun toString(): String {
        return "$calle  $ciudad ($codigoPostal)"
    }
}
data class ClienteUiState (

    val dni: String,
    val correo: String,
    val nombre: String,
    val telefono: String,
    val direccion: DireccionClienteUiState?,
    )
