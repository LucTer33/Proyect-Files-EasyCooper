package com.pmdm.tienda.ui.features.newuser.datospersonales

import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.github.pmdmiesbalmis.components.validacion.ValidacionCompuesta


data class ValidacionDatosPersonalesUiState(
    val validacionNombre: Validacion = object : Validacion {},
    val validacionDni: Validacion = object : Validacion {},
    val validacionTelefono: Validacion = object : Validacion {},
) : Validacion {
    private lateinit var validacionCompuesta : ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionNombre)
            .add(validacionDni)
            .add(validacionTelefono)
        return validacionCompuesta
    }
    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}
