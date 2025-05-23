package com.pmdm.EasyCooper.ui.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pmdm.EasyCooper.ui.features.pedidos.PedidosScreen
import com.pmdm.EasyCooper.ui.features.pedidos.PedidosViewModel
import kotlinx.serialization.Serializable


//const val PedidoRoute = "pedido/{dni}"

@Serializable
data class PedidoRoute(val dni:String)

fun NavGraphBuilder.pedidosDestination(
    pedidosViewModel: PedidosViewModel
) {

    composable<PedidoRoute>() { backStackEntry ->

        val dni= remember { backStackEntry.toRoute<PedidoRoute>().dni}
        pedidosViewModel.actualizaPedido(dni)

        PedidosScreen(
            pedidos = pedidosViewModel.pedidosUiState!!,
            pedidoSeleccionado = pedidosViewModel.pedidoSeleccionadoUiState,
            onClickPedido = pedidosViewModel.pedidoSeleccionadoEvent,
            onClickMuestraPedido = pedidosViewModel.muestraPedidoEvent,
            muestraPedidos = pedidosViewModel.muestraPedidos,
        )
    }
}


