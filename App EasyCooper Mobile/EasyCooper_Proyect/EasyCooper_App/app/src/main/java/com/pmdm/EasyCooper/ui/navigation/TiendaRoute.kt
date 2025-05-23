package com.pmdm.EasyCooper.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pmdm.EasyCooper.ui.features.easycooper.TiendaScreen
import com.pmdm.EasyCooper.ui.features.easycooper.TiendaViewModel
import kotlinx.serialization.Serializable



@Serializable
data class TiendaRoute(val correo : String)


fun NavGraphBuilder.tiendaDestination(
    tiendaViewModel: TiendaViewModel,
    onNavigateToPedido: (dni:String)->Unit,
    onNavigateToNewUser:(login:String)->Unit,
    onNavigateToLogin:()->Unit
) {

    composable<TiendaRoute> { backStackEntry ->
        val correo =backStackEntry.toRoute<TiendaRoute>().correo
        tiendaViewModel.actualizaCliente(correo)
        TiendaScreen(
            clienteUiState = tiendaViewModel.clienteState,
            articulos = tiendaViewModel.articulosState,
            muestraFavoritos = tiendaViewModel.mostrarFavoritoState,
            articuloSeleccionado = tiendaViewModel.articuloSeleccionadoState,
            filtro = tiendaViewModel.filtroState,
            estaFiltrando = tiendaViewModel.estaFiltrandoState,
            tallaUiState = tiendaViewModel.tallaUiState,
            carrito = tiendaViewModel.carritoState,
            numerArticulos = tiendaViewModel.numeroArticulosState,
            totalCompra = tiendaViewModel.totalCompraState,
            onTiendaEvent = tiendaViewModel::onTiendaEvent,
            pedido = tiendaViewModel.pedidoUiState,
            onTallaEvent = tiendaViewModel::onTallaEvent,
            onNavigateToPedido = onNavigateToPedido,
            onNavigateToNewUser = onNavigateToNewUser,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}
