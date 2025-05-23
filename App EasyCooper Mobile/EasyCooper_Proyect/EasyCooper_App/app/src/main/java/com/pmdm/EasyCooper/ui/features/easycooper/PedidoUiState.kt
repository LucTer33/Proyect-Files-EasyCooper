package com.pmdm.EasyCooper.ui.features.easycooper


data class ArticuloDePedidoUiState(
    val articuloId: Int=0,
    val url:String="",
    val descripcion:String="",
    val tamaño: Short=0,
    val precio: Float=0f,
    val cantidad: Int=0
)

data class PedidoUiState(
    val pedidoId: Long=0,
    val dniCliente: String="",
    val total: Float=0f,
    val fecha: Long=0,
    val articulos:MutableList<ArticuloDePedidoUiState>,
    val iniciado: Boolean=false
)

