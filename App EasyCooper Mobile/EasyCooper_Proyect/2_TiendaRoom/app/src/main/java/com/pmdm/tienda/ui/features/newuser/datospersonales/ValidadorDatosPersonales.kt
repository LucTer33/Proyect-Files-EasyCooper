package com.pmdm.tienda.ui.features.newuser.datospersonales

import com.github.pmdmiesbalmis.components.validacion.Validador
import com.github.pmdmiesbalmis.components.validacion.ValidadorCompuesto
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorDni
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorLongitudMaximaTexto
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorTelefono
import com.github.pmdmiesbalmis.components.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorDatosPersonales  @Inject constructor() : Validador<DatosPersonalesUiState> {
    val validadorDni = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El DNI no puede estar vacío"))
        .add(ValidadorDni("El DNI no es válido"))
    val validadorNombre = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("No nombre puede estar vacío"))
        .add(ValidadorLongitudMaximaTexto(20))
    val validadorTelefono = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("No teléfono puede estar vacío"))
        .add(ValidadorTelefono("El teléfono no es válido"))

    override fun valida(datos: DatosPersonalesUiState): ValidacionDatosPersonalesUiState {
        val validacionNombre = validadorNombre.valida(datos.nombre)
        val validacionDni = validadorDni.valida(datos.dni)
        val validacionTelefono = validadorTelefono.valida(datos.telefono)

        return ValidacionDatosPersonalesUiState(
            validacionNombre = validacionNombre,
            validacionDni = validacionDni,
            validacionTelefono = validacionTelefono
        )
    }

}