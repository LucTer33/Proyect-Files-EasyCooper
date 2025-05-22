package com.pmdm.tienda.data.mocks


import com.pmdm.tienda.data.room.articulo.ArticuloDao
import com.pmdm.tienda.data.room.articulo.inicializaArticulos
import com.pmdm.tienda.data.toArticulo
import com.pmdm.tienda.data.toArticulos

import com.pmdm.tienda.models.Articulo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ArticuloRepository @Inject constructor(
    private val proveedorArticulo: ArticuloDao
) {

    init {
        //Solo para la primera vez añadirá datos a la tabla artículos
        runBlocking { inicializaArticulos(proveedorArticulo) }
    }

    suspend fun get(): List<Articulo> = withContext(Dispatchers.IO) {
        proveedorArticulo.get().toMutableList().toArticulos()
    }

    suspend fun get(id: Int): Articulo? = withContext(Dispatchers.IO) {
        proveedorArticulo.get(id)?.toArticulo()
    }

    suspend fun get(filtro: String): List<Articulo>? = withContext(Dispatchers.IO) {
        proveedorArticulo.get(filtro)?.toMutableList()?.toArticulos()
    }

    suspend fun get(ids: MutableList<Int>): List<Articulo>? =
        withContext(Dispatchers.IO) {
            var lista = mutableListOf<Articulo>()
            ids.forEach { lista.add(proveedorArticulo.get(it).toArticulo()) }
            lista
        }
}