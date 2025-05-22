package com.pmdm.tienda.ui.features.pedidos


data class ArticuloConPedido(
    val url:String,
    val tamaño: Short,
    val precio: Float,
    val cantidad: Int
)

data class PedidoConArticuloUiState(
    val articulos:List<ArticuloConPedido>,
    val pedidoId: Long,
    val total: Float,
    val fecha: Long
)
