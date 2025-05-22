package com.pmdm.tienda.models


data class ArticuloDePedido(
    val articuloId: Int,
    val tamaño: Short,
    val cantidad: Int
)

data class Pedido(
    val pedidoId: Long,
    val dniCliente: String,
    val total: Float,
    val fecha: Long,
    val articulos: MutableList<ArticuloDePedido>
)
