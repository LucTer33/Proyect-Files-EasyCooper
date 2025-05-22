package com.pmdm.tienda.data


import com.pmdm.tienda.data.room.cliente.UsuarioDao
import com.pmdm.tienda.models.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UsuarioRepository @Inject constructor(
    private val proveedorUsuarios: UsuarioDao
) {
    suspend fun get(): List<Usuario> =
        withContext(Dispatchers.IO) { proveedorUsuarios.get().toMutableList().toUsuarios() }

    suspend fun get(login: String): Usuario? =
        withContext(Dispatchers.IO) { proveedorUsuarios.get(login)?.toUsuario() }

    suspend fun insert(usuario: Usuario) =
        withContext(Dispatchers.IO) { proveedorUsuarios.insert(usuario.toUsuarioEntity()) }

    suspend fun update(usuario: Usuario) =
        withContext(Dispatchers.IO) { proveedorUsuarios.update(usuario.toUsuarioEntity()) }
}