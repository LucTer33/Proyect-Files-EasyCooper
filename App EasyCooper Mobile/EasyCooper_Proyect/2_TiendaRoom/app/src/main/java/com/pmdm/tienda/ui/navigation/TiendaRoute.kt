package com.pmdm.tienda.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pmdm.tienda.ui.features.tienda.TiendaScreen
import com.pmdm.tienda.ui.features.tienda.TiendaViewModel
import kotlinx.serialization.Serializable

//const val TiendaRoute = "tienda/{correo}"

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
