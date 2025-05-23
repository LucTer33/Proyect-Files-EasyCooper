package com.pmdm.EasyCooper.ui.features.newuser.newuserpassword

import androidx.navigation.NavOptions

sealed interface NewUserPasswordEvent {
    data class LoginChanged(val login: String) : NewUserPasswordEvent
    data class PasswordChanged(val password: String) : NewUserPasswordEvent
    data class onClickCrearCliente(
        val onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?
    ) : NewUserPasswordEvent
}
