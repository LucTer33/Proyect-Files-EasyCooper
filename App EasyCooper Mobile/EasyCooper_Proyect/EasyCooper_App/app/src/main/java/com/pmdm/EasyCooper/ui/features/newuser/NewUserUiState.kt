package com.pmdm.EasyCooper.ui.features.newuser

import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.DatosPersonalesUiState
import com.pmdm.EasyCooper.ui.features.newuser.direccion.DireccionUiState
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.LoginPasswordUiState

data class NewUserUiState(
    val datosPersonalesUiState: DatosPersonalesUiState = DatosPersonalesUiState(),
    val direccionUiState: DireccionUiState = DireccionUiState(),
    val newUserPasswordUiState: LoginPasswordUiState = LoginPasswordUiState(),
)