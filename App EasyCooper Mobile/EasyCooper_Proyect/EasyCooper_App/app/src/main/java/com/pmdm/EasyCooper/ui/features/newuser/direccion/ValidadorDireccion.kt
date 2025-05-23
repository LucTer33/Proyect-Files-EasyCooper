package com.pmdm.EasyCooper.ui.features.newuser.direccion

import com.github.pmdmiesbalmis.components.validacion.Validador
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorDireccion @Inject constructor() : Validador<DireccionUiState> {

    val validadorCalle = ValidadorTextoNoVacio("La calle no puede estar vacía")
    val validadorCiudad = ValidadorTextoNoVacio("La ciudad no puede estar vacía")
    val validadorCodigoPostal = ValidadorTextoNoVacio("El código postal no puede estar vacío")

    override fun valida(direccionState: DireccionUiState): ValidacionDireccionUiState {
        val validacionCalle = validadorCalle.valida(direccionState.calle)
        val validacionCiudad = validadorCiudad.valida(direccionState.ciudad)
        val validacionCodigoPostal = validadorCodigoPostal.valida(direccionState.codigoPostal)

        return ValidacionDireccionUiState(
            validacionCalle = validacionCalle,
            validacionCiudad = validacionCiudad,
            validacionCodigoPostal = validacionCodigoPostal
        )
    }
}