package com.pmdm.tienda.ui.features.newuser.newuserpassword

data class LoginPasswordUiState(
    val login: String,
    val password: String,
) {
    constructor() : this(
        login = "",
        password = "",
    )
}