package com.pmdm.EasyCooper.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.EasyCooper.ui.features.newuser.NewUserScreen
import com.pmdm.EasyCooper.ui.features.newuser.NewUserViewModel
import kotlinx.serialization.Serializable


//const val NewUserRoute = "newUser"

@Serializable
object NewUserRoute

fun NavGraphBuilder.newUserDestination(
    newUserViewModel: NewUserViewModel,
    onNavigateToLogin: (correo: String, navOptions: NavOptions?) -> Unit
) {
    composable<NewUserRoute>() {

        NewUserScreen(
            esNuevoClienteState = newUserViewModel.esNuevoCliente,
            newUserUiState = newUserViewModel.newUserUiState,
            validacionNewUserUiState = newUserViewModel.validacionNewUserUiState,
            mostrarSnack = newUserViewModel.mostrarSnackState,
            mensaje = newUserViewModel.mensajeSnackBarState,
            incrementaPagina = newUserViewModel.incrementaPaginaState,
            onDireccionEvent = newUserViewModel::onDireccionEvent,
            onDatosPersonalesEvent = newUserViewModel::onDatosPersonalesEvent,
            onNewUserPasswordEvent = newUserViewModel::onNewUserPasswordEvent,
            onNavigateToLogin=onNavigateToLogin
        )
    }

}


