package com.pmdm.EasyCooper.ui.features.newuser

import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.github.pmdmiesbalmis.components.validacion.ValidacionCompuesta
import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.ValidacionDatosPersonalesUiState
import com.pmdm.EasyCooper.ui.features.newuser.direccion.ValidacionDireccionUiState
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.ValidacionLoginPasswordUiState


data class ValidacionNewUserUiState(
    val validacionDatosPersonalesUiState: ValidacionDatosPersonalesUiState = ValidacionDatosPersonalesUiState(),
    val validacionDireccionUiState: ValidacionDireccionUiState = ValidacionDireccionUiState(),
    val validacionLoginPasswordUiState: ValidacionLoginPasswordUiState = ValidacionLoginPasswordUiState()
) : Validacion {
    private lateinit var validacionCompuesta : ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionDatosPersonalesUiState)
            .add(validacionDireccionUiState)
            .add(validacionLoginPasswordUiState)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}
