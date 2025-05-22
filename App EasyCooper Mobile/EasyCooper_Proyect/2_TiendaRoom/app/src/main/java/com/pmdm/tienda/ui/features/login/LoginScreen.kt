package com.pmdm.tienda.ui.features.login

import TextNewAccount
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.login.ui.features.login.components.CircularImageFromResource
import com.pmdm.tienda.R

import com.pmdm.tienda.ui.features.login.components.UsuarioPassword
import com.pmdm.tienda.ui.theme.LoginTheme
import com.pmdm.tienda.ui.theme.Purple40
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    usuarioUiState: LoginUiState,
    validacionLoginUiState: ValidacionLoginUiState,
    mostrarSnack: Boolean,
    onLoginEvent: (LoginEvent) -> Unit,
    onMostrarSnackBar: () -> Unit,
    onNavigateToTienda: ((correo: String) -> Unit)? = null,
    onNavigateToNewUser: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    var recordarmeState by remember { mutableStateOf(false) }
    val snackbarHostState = remember{SnackbarHostState()}

    Scaffold (
        snackbarHost = {SnackbarHost(snackbarHostState)},
        content = { pading ->
        Box(modifier = Modifier.padding(pading)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {

                CircularImageFromResource(
                    idImageResource = R.drawable.login, contentDescription = "Imagen Login"
                )


                UsuarioPassword(modifier = Modifier.fillMaxWidth(),
                    loginState = usuarioUiState.login,
                    passwordState = usuarioUiState.password,
                    validacionLogin = validacionLoginUiState.validacionLogin,
                    validacionPassword = validacionLoginUiState.validacionPassword,
                    recordarmeState = recordarmeState,
                    onValueChangeLogin = {
                        onLoginEvent(LoginEvent.LoginChanged(it))
                    },
                    onValueChangePassword = {
                        onLoginEvent(LoginEvent.PasswordChanged(it))
                    },
                    onCheckedChanged = { recordarmeState = it },
                    onClickLogearse = {
                        onLoginEvent(LoginEvent.OnClickLogearse(onNavigateToTienda))
                        onMostrarSnackBar()
                        if(mostrarSnack) {
                            var mensaje = ""
                            if (validacionLoginUiState.hayError) mensaje =
                                validacionLoginUiState.mensajeError ?: ""
                            else if (!usuarioUiState.estaLogeado) mensaje = "Error, no existe el usuario o contraseña incorrecta"
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    message = mensaje,
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }
                    })
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Text(
                    "Olvidaste Password?",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    color = Purple40
                )
                Text("ó")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook",
                        alignment = Alignment.Center,
                        modifier = Modifier.size(35.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gmail),
                        contentDescription = "Gmail",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(3.dp)

                    )
                }
                TextNewAccount(onClick = {
                    onLoginEvent(LoginEvent.OnClickNewUser(onNavigateToNewUser))
                })
            }

        }
    }

    )
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val loginViewModel: LoginViewModel = viewModel()
    LoginTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {

            LoginScreen(usuarioUiState = loginViewModel.usuarioUiState,
                validacionLoginUiState = loginViewModel.validacionLoginUiState,
                onLoginEvent = loginViewModel::onLoginEvent,
                mostrarSnack = false,
                onNavigateToNewUser = {},
                onNavigateToTienda = {},
                onMostrarSnackBar = {})
        }
    }
}