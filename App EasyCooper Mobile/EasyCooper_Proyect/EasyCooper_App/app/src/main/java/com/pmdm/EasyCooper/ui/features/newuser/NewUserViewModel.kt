package com.pmdm.EasyCooper.ui.features.newuser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.EasyCooper.data.ClienteRepository
import com.pmdm.EasyCooper.data.UsuarioRepository
import com.pmdm.EasyCooper.models.Cliente
import com.pmdm.EasyCooper.models.Direccion
import com.pmdm.EasyCooper.models.Usuario
import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.DatosPersonalesEvent
import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.DatosPersonalesUiState
import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.ValidadorDatosPersonales
import com.pmdm.EasyCooper.ui.features.newuser.direccion.DireccionEvent
import com.pmdm.EasyCooper.ui.features.newuser.direccion.DireccionUiState
import com.pmdm.EasyCooper.ui.features.newuser.direccion.ValidadorDireccion
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.LoginPasswordUiState
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.NewUserPasswordEvent
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.ValidadorLoginPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository,
    private val usuarioRepository: UsuarioRepository,
    private val validadorDatosPersonales: ValidadorDatosPersonales,
    private val validadorDireccion: ValidadorDireccion,
    private val validadorNewUserPassword: ValidadorLoginPassword,
    private val validadorNewUser: ValidadorNewUser
) : ViewModel() {
    var esNuevoCliente by mutableStateOf(true)
    var mostrarSnackState by mutableStateOf(false)
    var mensajeSnackBarState by mutableStateOf("")
    var incrementaPaginaState by mutableStateOf(0)
    var estaCreadaCuenta: Boolean = false

    var newUserUiState by mutableStateOf(NewUserUiState())
    var validacionNewUserUiState by mutableStateOf(ValidacionNewUserUiState())

    fun onDatosPersonalesEvent(event: DatosPersonalesEvent) {

        when (event) {
            is DatosPersonalesEvent.NombreChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    datosPersonalesUiState = newUserUiState.datosPersonalesUiState.copy(nombre = event.nombre)
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDatosPersonalesUiState = validacionNewUserUiState.validacionDatosPersonalesUiState.copy(
                        validacionNombre = validadorDatosPersonales.validadorNombre.valida(event.nombre)
                    )
                )
            }

            is DatosPersonalesEvent.DniChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    datosPersonalesUiState = newUserUiState.datosPersonalesUiState.copy(
                        dni = event.dni,
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDatosPersonalesUiState = validacionNewUserUiState.validacionDatosPersonalesUiState.copy(
                        validacionDni = validadorDatosPersonales.validadorDni.valida(event.dni)
                    )
                )
            }

            is DatosPersonalesEvent.TelefonoChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    datosPersonalesUiState = newUserUiState.datosPersonalesUiState.copy(
                        telefono = event.telefono
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDatosPersonalesUiState = validacionNewUserUiState.validacionDatosPersonalesUiState.copy(
                        validacionTelefono = validadorDatosPersonales.validadorTelefono.valida(event.telefono)
                    )
                )
            }

            is DatosPersonalesEvent.OnClickSiguiente -> {
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDatosPersonalesUiState = validadorDatosPersonales.valida(
                        newUserUiState.datosPersonalesUiState
                    )
                )
                if (validacionNewUserUiState.validacionDatosPersonalesUiState.hayError) {
                    mensajeSnackBarState =
                        validacionNewUserUiState.validacionDatosPersonalesUiState.mensajeError!!
                    mostrarSnackState = true
                    incrementaPaginaState = 0

                } else {
                    mostrarSnackState = false
                    incrementaPaginaState = 1
                }
            }
        }
    }

    fun onDireccionEvent(event: DireccionEvent) {
        when (event) {
            is DireccionEvent.CalleChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    direccionUiState = newUserUiState.direccionUiState.copy(
                        calle = event.calle
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDireccionUiState = validacionNewUserUiState.validacionDireccionUiState.copy(
                        validacionCalle = validadorDireccion.validadorCalle.valida(event.calle)
                    )
                )
            }

            is DireccionEvent.CiudadChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    direccionUiState = newUserUiState.direccionUiState.copy(
                        ciudad = event.ciudad,
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDireccionUiState = validacionNewUserUiState.validacionDireccionUiState.copy(
                        validacionCiudad = validadorDireccion.validadorCiudad.valida(event.ciudad)
                    )
                )
            }

            is DireccionEvent.CodigoPostalChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    direccionUiState = newUserUiState.direccionUiState.copy(
                        codigoPostal = event.codiogPostal,
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDireccionUiState = validacionNewUserUiState.validacionDireccionUiState.copy(
                        validacionCodigoPostal = validadorDireccion.validadorCodigoPostal.valida(
                            event.codiogPostal
                        )
                    )
                )
            }

            is DireccionEvent.OnClickSiguiente -> {
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionDireccionUiState = validadorDireccion.valida(
                        newUserUiState.direccionUiState
                    )
                )
                if (validacionNewUserUiState.validacionDireccionUiState.hayError) {
                    mensajeSnackBarState =
                        validacionNewUserUiState.validacionDireccionUiState.mensajeError!!
                    mostrarSnackState = true
                    incrementaPaginaState = 0
                } else {
                    mostrarSnackState = false
                    incrementaPaginaState = 1
                }
            }
        }
    }

    fun onNewUserPasswordEvent(event: NewUserPasswordEvent) {
        when (event) {
            is NewUserPasswordEvent.LoginChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                        login = event.login
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionLoginPasswordUiState = validacionNewUserUiState.validacionLoginPasswordUiState.copy(
                        validacionLogin = validadorNewUserPassword.validacionLogin.valida(event.login)
                    )
                )
            }

            is NewUserPasswordEvent.PasswordChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                        password = event.password,
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionLoginPasswordUiState = validacionNewUserUiState.validacionLoginPasswordUiState.copy(
                        validacionPassword = validadorNewUserPassword.validacionPassword.valida(
                            event.password
                        )
                    )
                )
            }

            is NewUserPasswordEvent.onClickCrearCliente -> {
                viewModelScope.launch {
                    incrementaPaginaState = 0
                    mostrarSnackState = false
                    validacionNewUserUiState = validadorNewUser.valida(newUserUiState)
                    if (validacionNewUserUiState.hayError) {
                        mensajeSnackBarState = validacionNewUserUiState.mensajeError!!
                        mostrarSnackState = true
                    } else {
                        mostrarSnackState = true
                        creaCuenta()
                        if (estaCreadaCuenta) {
                            mensajeSnackBarState = "Cuenta creada correctamente"

                            event.onNavigateToLogin?.invoke(
                                newUserUiState.newUserPasswordUiState.login,
                                null
                            )
                        } else mensajeSnackBarState = "Ese cliente ya existe"
                    }
                }
            }
        }
    }

    suspend private fun creaCuenta() {

        estaCreadaCuenta = false

        val clientes = clienteRepository.get()
        val dniAnterior =
            clientes.find {
                it.dni == newUserUiState.datosPersonalesUiState.dni
            }
        val loginAnterior =
            clientes.find { it.correo == newUserUiState.newUserPasswordUiState.login }

        if (!esNuevoCliente && dniAnterior != null && loginAnterior != null) {
            val cliente = creaCliente()
            clienteRepository.update(cliente.toCliente())
            usuarioRepository.update(
                Usuario(
                    newUserUiState.newUserPasswordUiState.login,
                    newUserUiState.newUserPasswordUiState.password
                )
            )
            estaCreadaCuenta = true
        } else if (dniAnterior == null && loginAnterior == null) {
            val cliente = creaCliente()
            clienteRepository.insert(cliente.toCliente())
            usuarioRepository.insert(
                Usuario(
                    newUserUiState.newUserPasswordUiState.login,
                    newUserUiState.newUserPasswordUiState.password
                )
            )
            estaCreadaCuenta = true
        }
    }

    private fun creaCliente(): ClienteUiState {
        val direccion =
            DireccionClienteUiState(
                newUserUiState.direccionUiState.calle,
                newUserUiState.direccionUiState.ciudad,
                newUserUiState.direccionUiState.codigoPostal
            )
        val cliente = ClienteUiState(
            newUserUiState.datosPersonalesUiState.dni,
            newUserUiState.newUserPasswordUiState.login,
            newUserUiState.datosPersonalesUiState.nombre,
            newUserUiState.datosPersonalesUiState.telefono,
            direccion
        )
        return cliente
    }

    fun clearCliente() {
        newUserUiState = NewUserUiState()
        estaCreadaCuenta = false
        esNuevoCliente = true
    }

    fun inicializarCliente(cliente: Cliente) {
        viewModelScope.launch {
            newUserUiState = newUserUiState.copy(
                datosPersonalesUiState = DatosPersonalesUiState(
                    cliente.dni,
                    cliente.nombre,
                    cliente.telefono
                ),
                direccionUiState = DireccionUiState(
                    cliente.direccion?.calle!!,
                    cliente.direccion.ciudad!!,
                    cliente.direccion.codigoPostal!!
                ),

                newUserPasswordUiState = LoginPasswordUiState(
                    cliente.correo,
                    usuarioRepository.get(cliente.correo)?.password!!
                )
            )
        }
    }

    private fun DireccionClienteUiState.toDireccion() =
        Direccion(calle, ciudad, codigoPostal)

    private fun ClienteUiState.toCliente() = Cliente(
        this.dni,
        correo,
        nombre,
        telefono,
        direccion?.toDireccion(),
        emptyList<Int>().toMutableList()
    )
}