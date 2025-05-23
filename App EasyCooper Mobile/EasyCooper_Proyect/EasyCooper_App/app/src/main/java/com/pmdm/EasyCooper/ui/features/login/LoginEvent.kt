package com.pmdm.EasyCooper.ui.features.login

sealed interface LoginEvent {
    data class LoginChanged(val login: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data class  OnClickLogearse(val onNavigateTienda:((correo:String)->Unit)?):LoginEvent
    data class OnClickNewUser(val onNavigateToNewUser:() -> Unit):LoginEvent
}
