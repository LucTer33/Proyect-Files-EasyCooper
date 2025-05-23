package com.pmdm.EasyCooper.data

import com.pmdm.EasyCooper.data.room.pedido.PedidoDao
import com.pmdm.EasyCooper.data.room.pedido.PedidoEntity
import com.pmdm.EasyCooper.models.ArticuloDePedido
import com.pmdm.EasyCooper.models.Pedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PedidoRepository @Inject constructor(private val proveedorPedido: PedidoDao) {
    suspend fun get(): List<Pedido> =
        withContext(Dispatchers.IO) { proveedorPedido.getPedidos().toPedidos() }

    suspend fun get(dniCliente: String): List<Pedido> =
        withContext(Dispatchers.IO) { proveedorPedido.getPedidos(dniCliente).toPedidos() }

    suspend fun get(id: Long): Pedido? =
        withContext(Dispatchers.IO) { proveedorPedido.getPedido(id).toPedido() }

    suspend fun insert(pedido: Pedido) {
        withContext(Dispatchers.IO) {
            val id = proveedorPedido.insert(
                pedido.toPedidoEntity()
            )
            pedido.articulos.forEach {
                proveedorPedido.guardarArticuloPedido(
                    it.toArticuloConPedidoEntity(
                        id
                    )
                )
            }
        }
    }

    suspend fun List<PedidoEntity>.toPedidos(): List<Pedido> {
        return this.map { it.toPedido() }
    }

    suspend fun PedidoEntity.toPedido(): Pedido {

        val articulos = proveedorPedido.getArticulosConPedido(this.pedidoId)
        var articulosDePedido = mutableListOf<ArticuloDePedido>()
        articulos.forEach { it ->
            articulosDePedido.add(ArticuloDePedido(it.articuloId, it.tamaño.toShort(), it.cantidad))
        }
        return Pedido(
            this.pedidoId,
            this.dniCliente,
            this.total,
            this.fecha,
            articulosDePedido
        )
    }
}