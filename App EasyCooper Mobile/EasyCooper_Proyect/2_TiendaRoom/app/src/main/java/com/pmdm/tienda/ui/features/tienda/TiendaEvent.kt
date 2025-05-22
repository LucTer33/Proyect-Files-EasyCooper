package com.pmdm.tienda.ui.features.tienda

import com.pmdm.tienda.models.Articulo

sealed interface TiendaEvent {
    data class OnClickArticulo(val articulo: ArticuloUiState) : TiendaEvent
    data class OnClickAñadirCesta(var articulo: ArticuloDePedidoUiState) : TiendaEvent
    data class OnFiltroChange(var filtro: String) : TiendaEvent
    data class OnClickFiltrar(var filtro: String) : TiendaEvent
    data class OnClickFavorito(var articulo:ArticuloUiState) : TiendaEvent
    data class OnClickEstaFiltrando(var estaFiltrando: Boolean) : TiendaEvent
    data class OnClickMas(var articulo: ArticuloDePedidoUiState) : TiendaEvent
    data class OnClickMenos(var articulo: ArticuloDePedidoUiState) : TiendaEvent
    object OnDismissDialog : TiendaEvent
    object OnClickCasa : TiendaEvent
    object OnClickListarFavoritos : TiendaEvent
    object OnClickCarrito : TiendaEvent
    object OnClickComprar: TiendaEvent
    object OnClickSalir:TiendaEvent
}