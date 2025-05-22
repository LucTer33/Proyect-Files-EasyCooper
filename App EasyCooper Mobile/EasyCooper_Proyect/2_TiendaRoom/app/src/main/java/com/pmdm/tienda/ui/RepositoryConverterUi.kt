package com.pmdm.tienda.ui

import com.pmdm.tienda.models.Usuario
import com.pmdm.tienda.ui.features.login.LoginUiState

fun Usuario.toUsuarioUiState(logeado: Boolean) =
    LoginUiState(this.login, this.password, logeado)
//endregion

//region UsuarioUiState
fun LoginUiState.toUsuario() = Usuario(this.login, this.password)
//endregion