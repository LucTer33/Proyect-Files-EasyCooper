package com.pmdm.tienda.data.room.pedido

import androidx.room.*
import com.pmdm.tienda.data.room.articulo.ArticuloEntity


//RELACIÓN MUCHOS A MUCHOS (un articulo puede estar en muchos pedidos y a la inversa)
data class PedidosConArticulos(
    @Embedded val pedido: PedidoEntity,
    @Relation(
        parentColumn = "pedidoId",
        entityColumn = "articuloId",
        associateBy = Junction(ArticuloConPedidoEntity::class)
    )
    val articulos: List<ArticuloEntity>
)

//ENTIDAD PARA CREAR LA RELACIÓN MUCHOS A MUCHOS ENTRE ARTICULOS Y PEDIDOS
@Entity(tableName = "articulopedido",primaryKeys = ["articuloId", "pedidoId"])
data class ArticuloConPedidoEntity (
    @ColumnInfo(name = "articuloId")
    val articuloId: Int,
    @ColumnInfo(name = "pedidoId")
    val pedidoId: Long,
    @ColumnInfo(name="tamanyo")
    val tamaño:Int,
    @ColumnInfo(name="cantidad")
    val cantidad:Int)