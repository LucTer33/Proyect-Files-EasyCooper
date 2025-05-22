package com.pmdm.tienda.data


import com.pmdm.tienda.data.room.pedido.ArticuloConPedidoEntity
import com.pmdm.tienda.data.room.pedido.PedidoEntity
import com.pmdm.tienda.data.room.articulo.ArticuloEntity
import com.pmdm.tienda.data.room.cliente.ClienteEntity
import com.pmdm.tienda.data.room.cliente.UsuarioEntity
import com.pmdm.tienda.models.Articulo
import com.pmdm.tienda.models.ArticuloDePedido
import com.pmdm.tienda.models.Cliente
import com.pmdm.tienda.models.Direccion
import com.pmdm.tienda.models.Pedido

import com.pmdm.tienda.models.Usuario


//region usuarioEntity
fun UsuarioEntity.toUsuario(): Usuario = Usuario(this.login, this.password)
fun MutableList<UsuarioEntity>.toUsuarios(): List<Usuario> =
    this.map { it.toUsuario() }
fun Usuario.toUsuarioEntity(): UsuarioEntity= UsuarioEntity(this.login, this.password)
//endregion
//region ClienteEntity
fun MutableList<Cliente>.toClientesEntity(): List<ClienteEntity> =
    this.map { it.toClienteEntity() }

fun Cliente.toClienteEntity(): ClienteEntity =
    ClienteEntity(
        this.dni, this.correo, this.nombre, this.telefono, emptyList<Int>().toMutableList(),
        com.pmdm.tienda.data.room.cliente.Direccion(
            this.direccion?.calle,
            this.direccion?.ciudad,
            this.direccion?.codigoPostal,
        ),
    )

fun List<ClienteEntity>.toClientes(): List<Cliente> =
    this.map { it.toCliente() }

fun ClienteEntity.toCliente(): Cliente = Cliente(
    this.dni,
    this.correo,
    this.nombre,
    this.telefono,
    Direccion(this.direccion?.calle, this.direccion?.ciudad, this.direccion?.codigoPostal),
    this.favoritos.toMutableList()
)
//endregion
//region ArticuloEntity
fun ArticuloEntity.toArticulo(): Articulo =
    Articulo(this.id, this.url, this.precio, this.descripcion)

fun MutableList<ArticuloEntity>.toArticulos(): List<Articulo> =
    this.map { it.toArticulo() }

fun Articulo.toArticuloEntity(): ArticuloEntity =
    ArticuloEntity(this.id, this.url, this.descripcion, this.precio,)

//endregion
//region PedidoEntity



fun Pedido.toPedidoEntity(): PedidoEntity =
    PedidoEntity(this.pedidoId, this.dniCliente, this.total, this.fecha)


//enregion

//region ArticuloDePedido
fun ArticuloDePedido.toArticuloConPedidoEntity(idPedido: Long) =
    ArticuloConPedidoEntity(this.articuloId, idPedido, this.tamaño.toInt(), this.cantidad)

fun List<ArticuloDePedido>.toArticulosConPedidoEntity(idPedido: Long) =
    this.map { it.toArticuloConPedidoEntity(idPedido) }
fun ArticuloConPedidoEntity.toArticuloDePedido() =
    ArticuloDePedido(this.articuloId, this.tamaño.toShort(), this.cantidad)

fun List<ArticuloConPedidoEntity>.toArticuloDePedido(idPedido: Long) =
    this.map { it.toArticuloDePedido() }

//endregion