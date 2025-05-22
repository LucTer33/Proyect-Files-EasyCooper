package com.pmdm.tienda.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pmdm.tienda.data.room.articulo.ArticuloDao
import com.pmdm.tienda.data.room.articulo.ArticuloEntity
import com.pmdm.tienda.data.room.cliente.ClienteDao
import com.pmdm.tienda.data.room.cliente.ClienteEntity
import com.pmdm.tienda.data.room.cliente.UsuarioDao
import com.pmdm.tienda.data.room.cliente.UsuarioEntity
import com.pmdm.tienda.data.room.pedido.ArticuloConPedidoEntity
import com.pmdm.tienda.data.room.pedido.PedidoDao
import com.pmdm.tienda.data.room.pedido.PedidoEntity

class ConvertidorDeTipo {
    @TypeConverter
    fun deListaDeEnterosAString(lista: List<Int>?): String {
        return Gson().toJson(lista)
    }

    @TypeConverter
    fun deStringAListaDeEnteros(cadena: String?): List<Int>? {
        val tipo = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(cadena, tipo)
    }
}
@TypeConverters(ConvertidorDeTipo::class)
@Database(
    entities = [UsuarioEntity::class, ClienteEntity::class, ArticuloEntity::class, PedidoEntity::class, ArticuloConPedidoEntity::class],
    version = 1
)
abstract class TiendaDB : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun clienteDao(): ClienteDao
    abstract fun articuloDao(): ArticuloDao
    abstract fun pedidoDao(): PedidoDao
    companion object {
        @Volatile
        private var db: TiendaDB? = null
        fun getDatabase(context: Context) = Room.databaseBuilder(
            context,
            TiendaDB::class.java, "tienda"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}