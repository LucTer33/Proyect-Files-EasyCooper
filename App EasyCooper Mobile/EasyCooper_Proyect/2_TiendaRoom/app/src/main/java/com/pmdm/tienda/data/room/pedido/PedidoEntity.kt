package com.pmdm.tienda.data.room.pedido

import androidx.annotation.NonNull
import androidx.room.*
import com.pmdm.tienda.data.room.cliente.ClienteEntity


//CLAVE AJENA SOBRE EL CAMPO DNICLIENTE AL CAMPO DNI DE LA TABLA CLIENTE
@Entity(tableName = "pedidos",
    foreignKeys = arrayOf(
        ForeignKey(entity = ClienteEntity::class,
            parentColumns = arrayOf("dni"),
            childColumns = arrayOf("dniCliente"),
            onDelete = ForeignKey.CASCADE
        )
    ))
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) // El id será autogenerado.
    @ColumnInfo(name = "pedidoId")
    val pedidoId: Long,
    @ColumnInfo(name = "dniCliente")
    val dniCliente: String,
    @ColumnInfo(name = "total")
    val total: Float,
    @NonNull
    @ColumnInfo(name = "fecha")
    // Indicamos que siempre debe tener una fecha.
    val fecha: Long
)
