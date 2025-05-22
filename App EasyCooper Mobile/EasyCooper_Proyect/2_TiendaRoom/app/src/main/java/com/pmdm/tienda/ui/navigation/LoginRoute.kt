package com.pmdm.tienda.ui.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.tienda.ui.features.login.LoginScreen
import com.pmdm.tienda.ui.features.login.LoginViewModel
import kotlinx.serialization.Serializable




@Serializable
data class LoginRoute(val correo: String)

fun NavGraphBuilder.loginDestination(
    loginViewModel: LoginViewModel,
    onNavigateToNewUser: () -> Unit,
    onNavigateToTienda: (correo: String) -> Unit,
) {
    composable<LoginRoute>() { backStackEntry ->

        LoginScreen(
            usuarioUiState = loginViewModel.usuarioUiState,
            validacionLoginUiState = loginViewModel.validacionLoginUiState,
            onLoginEvent = loginViewModel::onLoginEvent,
            mostrarSnack = loginViewModel.mostrarSnackBar,
            onMostrarSnackBar = loginViewModel.onMostrarSnackBar,
            onNavigateToTienda = onNavigateToTienda,
            onNavigateToNewUser = onNavigateToNewUser
        )
    }
}
