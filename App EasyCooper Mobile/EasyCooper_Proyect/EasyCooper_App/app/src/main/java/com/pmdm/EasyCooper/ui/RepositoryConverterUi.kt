package com.pmdm.EasyCooper.ui

import com.pmdm.EasyCooper.models.Usuario
import com.pmdm.EasyCooper.ui.features.login.LoginUiState

fun Usuario.toUsuarioUiState(logeado: Boolean) =
    LoginUiState(this.login, this.password, logeado)
//endregion

//region UsuarioUiState
fun LoginUiState.toUsuario() = Usuario(this.login, this.password)
//endregion