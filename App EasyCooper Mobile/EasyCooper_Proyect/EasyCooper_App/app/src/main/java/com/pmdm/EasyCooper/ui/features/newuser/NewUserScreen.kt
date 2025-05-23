package com.pmdm.EasyCooper.ui.features.newuser

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.DatosPersonales
import com.pmdm.EasyCooper.ui.features.newuser.direccion.Direccion
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.NuevoUsuarioPassword
import com.pmdm.EasyCooper.ui.features.newuser.datospersonales.DatosPersonalesEvent
import com.pmdm.EasyCooper.ui.features.newuser.direccion.DireccionEvent
import com.pmdm.EasyCooper.ui.features.newuser.newuserpassword.NewUserPasswordEvent
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewUserScreen(
    esNuevoClienteState:Boolean,
    newUserUiState: NewUserUiState,
    validacionNewUserUiState: ValidacionNewUserUiState,
    mostrarSnack: Boolean,
    mensaje: String,
    incrementaPagina: Int,
    onDireccionEvent: (DireccionEvent) -> Unit,
    onDatosPersonalesEvent: (DatosPersonalesEvent) -> Unit,
    onNewUserPasswordEvent: (NewUserPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOxptions: NavOptions?) -> Unit)?
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })

    Box(
        modifier = Modifier.fillMaxHeight(),

        ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .wrapContentHeight()
                .padding(25.dp)

        ) { pagina ->

            when (pagina) {
                0 -> DatosPersonales(
                    datosPersonalesUIState = newUserUiState.datosPersonalesUiState,
                    validadorDatosPersonalesUIState = validacionNewUserUiState.validacionDatosPersonalesUiState,
                    datosPersonalesEvent = {
                        onDatosPersonalesEvent(it)
                        if (it is DatosPersonalesEvent.OnClickSiguiente) {
                            scope.launch {
                                pagerState.scrollToPage(
                                    pagina + incrementaPagina
                                )
                            }
                        }
                    })

                1 -> Direccion(direccionUiState = newUserUiState.direccionUiState,
                    validadorDireccionUiState = validacionNewUserUiState.validacionDireccionUiState,
                    direccionEvent = {
                        onDireccionEvent(it)
                        if (it is DireccionEvent.OnClickSiguiente) {
                            scope.launch {
                                pagerState.scrollToPage(
                                    pagina + incrementaPagina
                                )
                            }
                        }
                    })

                2 -> NuevoUsuarioPassword(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState,
                    validadorNewUserUiState = validacionNewUserUiState.validacionLoginPasswordUiState,
                    newUserPasswordEvent = onNewUserPasswordEvent,
                    onNavigateToLogin=onNavigateToLogin
                )
            }
        }

        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(20.dp)
                )
            }
        }
        if (mostrarSnack) {
            if (mensaje.isNotEmpty()) Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = mensaje)
            }
        }
    }
}



