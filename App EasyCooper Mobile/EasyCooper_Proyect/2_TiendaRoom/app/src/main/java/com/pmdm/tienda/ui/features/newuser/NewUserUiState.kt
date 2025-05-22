package com.pmdm.tienda.ui.features.newuser

import com.pmdm.tienda.ui.features.newuser.datospersonales.DatosPersonalesUiState
import com.pmdm.tienda.ui.features.newuser.direccion.DireccionUiState
import com.pmdm.tienda.ui.features.newuser.newuserpassword.LoginPasswordUiState

data class NewUserUiState(
    val datosPersonalesUiState: DatosPersonalesUiState = DatosPersonalesUiState(),
    val direccionUiState: DireccionUiState = DireccionUiState(),
    val newUserPasswordUiState: LoginPasswordUiState = LoginPasswordUiState(),
)