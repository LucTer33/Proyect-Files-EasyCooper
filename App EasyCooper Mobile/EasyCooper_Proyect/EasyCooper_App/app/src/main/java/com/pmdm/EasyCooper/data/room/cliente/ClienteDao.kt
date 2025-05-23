package com.pmdm.EasyCooper.data.room.cliente

import androidx.room.*
import com.pmdm.EasyCooper.data.room.pedido.PedidoEntity


//RELACIÓN UNO A MUCHOS (un cliente puede tener muchos pedidos)
data class ClienteConPedidos(
    @Embedded val cliente: ClienteEntity,
    @Relation(
        parentColumn = "dni",
        entityColumn = "dniCliente"
    )
    val pedidos: List<PedidoEntity>
)

@Dao
interface ClienteDao {
    @Delete
    suspend fun delete(cliente: ClienteEntity)
    @Query("SELECT * FROM CLIENTES")
    suspend fun get(): List<ClienteEntity>

    @Query("SELECT * FROM clientes WHERE correo IN (:login)")
    suspend fun get(login:String): ClienteEntity


    @Query("SELECT * FROM CLIENTES WHERE dni IN (:clienteDni)")
    suspend fun getFromDni(clienteDni: String): ClienteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cliente: ClienteEntity)

    @Update
    suspend fun update(cliente: ClienteEntity)
    //RECUPERAMOS LA INFORMACIÓN DE LA RELACION 1M

    @Query("UPDATE clientes SET favoritos = :favoritos WHERE dni = :dni")
    suspend fun actualizaFavoritos(dni: String, favoritos: List<Int>)


    @Transaction
    // No indicamos la relación en la consulta ya está definida
    // en el objeto a recuperar. Lista de objetos ClienteConPedidos
    @Query("SELECT * FROM clientes WHERE dni=(:clienteDni)")
    suspend fun getPedidosCliente(clienteDni: String): List<ClienteConPedidos>

}