package com.pmdm.EasyCooper.data.room.pedido

import androidx.room.*


@Dao
interface PedidoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pedido: PedidoEntity):Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarArticuloPedido(pedido: ArticuloConPedidoEntity)

    @Query("SELECT * FROM pedidos")
    suspend fun getPedidos(): List<PedidoEntity>

    @Query("SELECT * FROM pedidos WHERE dniCliente=(:dniCliente)")
    suspend fun getPedidos(dniCliente: String): List<PedidoEntity>

    @Transaction
    @Query("SELECT * FROM pedidos WHERE pedidoId=(:pedidoId)")
    suspend fun getPedido(pedidoId:Long): PedidoEntity

    @Query("SELECT * FROM articulopedido WHERE pedidoId=(:pedidoId)")
    suspend fun getArticulosConPedido(pedidoId:Long): List<ArticuloConPedidoEntity>

    @Query("SELECT cantidad FROM articulopedido WHERE pedidoId=:pedidoId AND articuloId=:articuloId")
    suspend fun getCantidadArticulosEnPedido(pedidoId: Long, articuloId:Int): Int

}
