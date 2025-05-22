package com.pmdm.tienda.ui.features.tienda

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pmdm.tienda.models.Cliente
import com.pmdm.tienda.ui.features.tienda.components.BarraNavegacion
import com.pmdm.tienda.ui.features.tienda.components.Carrito
import com.pmdm.tienda.ui.features.tienda.components.Escaparate
import com.pmdm.tienda.ui.features.tienda.components.BarraSuperior
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TiendaScreen(
    clienteUiState: Cliente,
    articulos: List<ArticuloUiState>,
    articuloSeleccionado: ArticuloUiState?,
    filtro: String,
    muestraFavoritos: Boolean,
    estaFiltrando: Boolean,
    carrito: Boolean,
    numerArticulos: Int,
    totalCompra: Float,
    pedido: PedidoUiState,
    tallaUiState: TallaUiState,
    onTallaEvent: (TallaEvent) -> Unit,
    onTiendaEvent: (TiendaEvent) -> Unit,
    onNavigateToPedido: (String) -> Unit,
    onNavigateToNewUser: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var salirState by remember {
        mutableStateOf(true)
    }
    val contexto = LocalContext.current
    Scaffold(
        topBar = {
            BarraSuperior(
                filtro = filtro,
                estaFiltrando = estaFiltrando,
                totalCompra = totalCompra,
                carrito = carrito,
                clienteUiState = clienteUiState,
                onFiltroChange = { onTiendaEvent(TiendaEvent.OnFiltroChange(it)) },
                onClickFiltrar = { onTiendaEvent(TiendaEvent.OnClickFiltrar(it)) },
                onEstaFiltrandoChange = { onTiendaEvent(TiendaEvent.OnClickEstaFiltrando(it)) },
                onClickUsuario = { opcion ->
                    if (opcion == 0) onNavigateToNewUser(clienteUiState.correo)
                    else {
                        onNavigateToLogin()
                        onTiendaEvent(TiendaEvent.OnClickSalir)
                    }
                },
                onClickCompar = { onTiendaEvent(TiendaEvent.OnClickComprar) },
                onClickCasa = { onTiendaEvent(TiendaEvent.OnClickCasa) }
            )
        },
        bottomBar = {
            BarraNavegacion(
                numeroArticulos = numerArticulos,
                onClickFavoritos = { onTiendaEvent(TiendaEvent.OnClickListarFavoritos) },
                onClickCasa = {
                    onTiendaEvent(TiendaEvent.OnClickCasa)
                },
                onClickCarrito = { onTiendaEvent(TiendaEvent.OnClickCarrito) },
                onClickPedidos = { onNavigateToPedido(clienteUiState.dni) }
            )
        },
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
        )
        {
            if (!carrito) {
                Escaparate(
                    articulos = articulos,
                    articuloSeleccionado = articuloSeleccionado,
                    tallaUiState = tallaUiState,
                    onTallaEvent = onTallaEvent,
                    onTiendaEvent = onTiendaEvent
                )
            } else Carrito(pedido = pedido, onTiendaEvent = onTiendaEvent)
        }
        BackHandler(salirState) {
            if (carrito || estaFiltrando || muestraFavoritos) {
                onTiendaEvent(TiendaEvent.OnClickCasa)
                salirState = true
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Pulse otra vez para salir de la tienda", duration = SnackbarDuration.Short
                    )
                    salirState = false
                }
                /*      scope.launch {
                          delay(3000)
                          salirState = true
                      }*/
            }

        }

    }
}