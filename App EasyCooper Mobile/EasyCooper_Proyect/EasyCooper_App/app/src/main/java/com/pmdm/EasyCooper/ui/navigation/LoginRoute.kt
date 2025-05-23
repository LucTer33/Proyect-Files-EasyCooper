package com.pmdm.EasyCooper.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.EasyCooper.ui.features.login.LoginScreen
import com.pmdm.EasyCooper.ui.features.login.LoginViewModel
import kotlinx.serialization.Serializable




@Serializable
data class LoginRoute(val correo: String)

fun NavGraphBuilder.loginDestination(
    loginViewModel: LoginViewModel,
    onNavigateToNewUser: () -> Unit,
    onNavigateToTienda: (correo: String) -> Unit,
) {
    composable<LoginRoute>() { backStackEntry ->
// No se pasan directamente los ViewModel apartir de aqui, solo lo que necesiten las ventanas
        LoginScreen(
            usuarioUiState = loginViewModel.usuarioUiState,
            validacionLoginUiState = loginViewModel.validacionLoginUiState,
            onLoginEvent = loginViewModel::onLoginEvent,//Lo que pasaria si el usuario es valido
            mostrarSnack = loginViewModel.mostrarSnackBar,
            onMostrarSnackBar = loginViewModel.onMostrarSnackBar,//Activa Snackbar desde login
            onNavigateToTienda = onNavigateToTienda,//Redirige a tienda
            onNavigateToNewUser = onNavigateToNewUser//Redirige a crear nuevo usuario
        )
    }
}
