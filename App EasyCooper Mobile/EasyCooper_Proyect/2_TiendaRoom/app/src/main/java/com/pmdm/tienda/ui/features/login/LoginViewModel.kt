package com.pmdm.tienda.ui.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.tienda.data.UsuarioRepository
import com.pmdm.tienda.models.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorLogin
) : ViewModel() {

    var usuarioUiState by mutableStateOf(LoginUiState())
        private set
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set
    var mostrarSnackBar by mutableStateOf(false)
    val onMostrarSnackBar: () -> Unit by mutableStateOf({
        mostrarSnackBar = !mostrarSnackBar
    })

    fun onLoginEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.LoginChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    login = loginEvent.login
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionLogin = validadorLogin.validadorLogin.valida(loginEvent.login)
                )
            }

            is LoginEvent.PasswordChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    password = loginEvent.password
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionPassword = validadorLogin.validadorPassword.valida(loginEvent.password)
                )
            }

            is LoginEvent.OnClickNewUser -> {
                loginEvent.onNavigateToNewUser.invoke()
            }

            is LoginEvent.OnClickLogearse -> {
                viewModelScope.launch {
                    validacionLoginUiState = validadorLogin.valida(usuarioUiState)
                    if (!validacionLoginUiState.hayError) {

                        usuarioUiState = usuarioUiState.copy(
                            estaLogeado = logearse()
                        )
                        if (usuarioUiState.estaLogeado) {
                            delay(3000)
                            loginEvent.onNavigateTienda?.let { it(usuarioUiState.login) }
                        }
                    }
                }
            }
        }
    }

    suspend fun logearse(): Boolean {
        val usuario = usuarioUiState.toUsuario()
        usuarioRepository.get().forEach {
            if (usuario.login == it.login && usuario.password == it.password) return true
        }
        return false
    }

    fun iniciaUsuario(correo: String?) {
        if (correo != null) usuarioUiState = LoginUiState(correo, "", false)
        else usuarioUiState = LoginUiState()
        mostrarSnackBar=false
    }

    fun clearLogin() {
        usuarioUiState = LoginUiState()
    }

    fun LoginUiState.toUsuario() = Usuario(this.login, this.password)
}