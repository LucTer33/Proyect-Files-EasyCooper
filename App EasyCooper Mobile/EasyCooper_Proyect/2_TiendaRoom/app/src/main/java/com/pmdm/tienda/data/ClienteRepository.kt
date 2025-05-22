package com.pmdm.tienda.data

import com.pmdm.tienda.data.room.cliente.ClienteDao
import com.pmdm.tienda.models.Cliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ClienteRepository @Inject constructor(private val proveedorClientes: ClienteDao) {

    suspend fun get(): List<Cliente> =
        withContext(Dispatchers.IO) { proveedorClientes.get().toClientes() }

    suspend fun get(login: String): Cliente = withContext(Dispatchers.IO) {
        proveedorClientes.get(login).toCliente()
    }

    suspend fun insert(cliente: Cliente) =
        withContext(Dispatchers.IO) { proveedorClientes.insert(cliente.toClienteEntity()) }

    suspend fun update(cliente: Cliente) =
        withContext(Dispatchers.IO) { proveedorClientes.update(cliente.toClienteEntity()) }

    suspend fun actualizaFavoritos(dni: String, favoritos: List<Int>) =
        withContext(Dispatchers.IO) {
            proveedorClientes.actualizaFavoritos(dni, favoritos)
        }

}

