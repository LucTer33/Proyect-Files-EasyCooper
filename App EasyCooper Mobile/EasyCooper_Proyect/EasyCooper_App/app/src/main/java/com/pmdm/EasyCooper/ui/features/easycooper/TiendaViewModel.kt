package com.pmdm.EasyCooper.ui.features.easycooper

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.EasyCooper.data.ClienteRepository
import com.pmdm.EasyCooper.data.PedidoRepository
import com.pmdm.EasyCooper.data.mocks.ArticuloRepository
import com.pmdm.EasyCooper.models.Articulo
import com.pmdm.EasyCooper.models.ArticuloDePedido
import com.pmdm.EasyCooper.models.Cliente
import com.pmdm.EasyCooper.models.Direccion
import com.pmdm.EasyCooper.models.Pedido
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TiendaViewModel @Inject constructor(
    private val articuloRepository: ArticuloRepository,
    private val clienteRepository: ClienteRepository,
    private val pedidoRepository: PedidoRepository
) : ViewModel() {


    val login = "pepe@gmail.com"

    var clienteState by mutableStateOf(Cliente())


    var filtroState by mutableStateOf("")
    var carritoState by mutableStateOf(false)
    var numeroArticulosState by mutableStateOf(0)
    var totalCompraState by mutableStateOf(0f)
    var estaFiltrandoState by mutableStateOf(false)

    var pedidoUiState by mutableStateOf(iniciarNuevoPedido())
    var articulodDePedidoUiState: ArticuloDePedidoUiState by mutableStateOf(ArticuloDePedidoUiState())
    var mostrarFavoritoState by mutableStateOf(false)


    var articulosState by mutableStateOf(mutableListOf<ArticuloUiState>().toMutableStateList())

    var tallaUiState: TallaUiState by mutableStateOf(inicializaTalla())
    var articuloSeleccionadoState: ArticuloUiState? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            articulosState = getArticulos()
            clienteState = inicializaCliente(null)
        }
    }

    fun onTallaEvent(tallaEvent: TallaEvent) {

        when (tallaEvent) {
            is TallaEvent.OnClickPequeña -> tallaUiState = inicializaTalla(TipoTalla.PEQUEÑA)

            is TallaEvent.OnClickMediana -> tallaUiState = inicializaTalla(TipoTalla.MEDIANA)

            is TallaEvent.OnClickGrande -> tallaUiState = inicializaTalla(TipoTalla.GRANDE)

            is TallaEvent.OnClickXGrande -> tallaUiState = inicializaTalla(TipoTalla.XGRANDE)
        }
    }

    fun onTiendaEvent(tiendaEvent: TiendaEvent) {
        when (tiendaEvent) {
            is TiendaEvent.OnClickArticulo -> {
                if (articuloSeleccionadoState?.id == tiendaEvent.articulo.id) articuloSeleccionadoState =
                    null
                else articuloSeleccionadoState = tiendaEvent.articulo
            }

            is TiendaEvent.OnDismissDialog -> {
                articuloSeleccionadoState = null
            }

            is TiendaEvent.OnClickAñadirCesta -> {
                articulodDePedidoUiState = tiendaEvent.articulo
                if (articulodDePedidoUiState != null) {
                    var seleccion: TipoTalla = TipoTalla.NOTALLA

                    for (t in tallaUiState.tallaSeleccionada) if (t.value) seleccion = t.key
                    if (seleccion != TipoTalla.NOTALLA) {
                        articulodDePedidoUiState =
                            articulodDePedidoUiState.copy(tamaño = seleccion.ordinal.toShort())
                        val articuloAux = buscaArticuloEnPedido(articulodDePedidoUiState)
                        var aux= pedidoUiState.articulos.toMutableList()
                        if (articuloAux != null) {
                            val posicion = buscaPosicionArticuloPedido(articuloAux)

                            aux[posicion] =
                                pedidoUiState.articulos[posicion].copy(
                                    cantidad = articuloAux.cantidad +
                                            pedidoUiState.articulos[posicion].cantidad
                                )
                        } else aux.add(articulodDePedidoUiState!!)
                        pedidoUiState=pedidoUiState.copy(articulos = aux)
                        actualizarCifrasPedido()
                        tallaUiState = inicializaTalla()
                    }
                }
            }

            is TiendaEvent.OnFiltroChange -> {
                filtroState = tiendaEvent.filtro
            }

            is TiendaEvent.OnClickFiltrar -> {
                viewModelScope.launch {
                    articulosState = getArticulos(tiendaEvent.filtro)?: getArticulos()
                }
            }

            is TiendaEvent.OnClickCasa -> {
                viewModelScope.launch {
                    articulosState = getArticulos()
                    filtroState = ""
                    estaFiltrandoState = false
                    carritoState = false
                    mostrarFavoritoState = false
                }
            }

            is TiendaEvent.OnClickEstaFiltrando -> {
                estaFiltrandoState = tiendaEvent.estaFiltrando
            }

            is TiendaEvent.OnClickFavorito -> {
                viewModelScope.launch {
                    val posicion =
                        articulosState.indexOf(articulosState.find { a -> tiendaEvent.articulo.id == a.id })
                    if (posicion != -1) {
                        val favorito = articulosState[posicion].favorito
                        articulosState[posicion] =
                            articulosState[posicion].copy(favorito = !favorito)

                        if (!clienteState.favoritos.contains(tiendaEvent.articulo.id)) clienteState.favoritos.add(
                            tiendaEvent.articulo.id
                        )
                        else clienteState.favoritos.remove(tiendaEvent.articulo.id)
                        actualizaFavoritos()
                    }
                }

            }

            is TiendaEvent.OnClickListarFavoritos -> {
                viewModelScope.launch {
                    mostrarFavoritoState = !mostrarFavoritoState
                    if (mostrarFavoritoState)
                        articulosState = getArticulosFavoritos().toMutableStateList()
                    else articulosState = getArticulos()
                    carritoState = false
                }
            }

            is TiendaEvent.OnClickCarrito -> {
                if (carritoState == false) carritoState = true
            }

            is TiendaEvent.OnClickMas -> {
                val posicion = buscaPosicionArticuloPedido(tiendaEvent.articulo)
                var aux = pedidoUiState.articulos.toMutableList()
                aux[posicion] =
                    aux[posicion].copy(cantidad = pedidoUiState.articulos[posicion].cantidad + 1)
                pedidoUiState = pedidoUiState.copy(articulos = aux)
                actualizarCifrasPedido()
            }

            is TiendaEvent.OnClickMenos -> {
                val posicion = buscaPosicionArticuloPedido(tiendaEvent.articulo)
                var aux = pedidoUiState.articulos.toMutableList()
                if (pedidoUiState.articulos[posicion].cantidad - 1 > 0) {
                    aux[posicion] =
                        aux[posicion].copy(cantidad = pedidoUiState.articulos[posicion].cantidad - 1)
                } else aux.removeAt(posicion)
                pedidoUiState = pedidoUiState.copy(articulos = aux)
                actualizarCifrasPedido()
            }

            is TiendaEvent.OnClickComprar -> {
                viewModelScope.launch {
                    val p = pedidoUiState.toPedido()
                    pedidoRepository.insert(p)
                    numeroArticulosState = 0
                    pedidoUiState = iniciarNuevoPedido()
                    carritoState = false
                    actualizarCifrasPedido()
                }
            }

            is TiendaEvent.OnClickSalir -> {
                clearTienda()
            }
        }
    }

    suspend private fun getArticulos() =
        articuloRepository.get().toMutableList().toArticulosUiState().checkFavoritos()
            .toMutableStateList()


    suspend private fun getArticulos(filtro: String) =
        articuloRepository.get(filtro)?.toMutableList()?.toArticulosUiState()?.checkFavoritos()
            ?.toMutableStateList()


    /*suspend private fun getArticulosFavoritos(): MutableList<ArticuloUiState>? {
        return articuloRepository.get(clienteState.favoritos)?.toMutableList()?.toArticulosUiState()
            ?.checkFavoritos()
    }*/
    suspend private fun getArticulosFavoritos(): MutableList<ArticuloUiState> {
        val lista = clienteRepository.get(clienteState.correo).favoritos
        return getArticulos().filter { lista.contains(it.id) }.toMutableList()
    }

    suspend private fun actualizaFavoritos() {
        clienteRepository.actualizaFavoritos(clienteState.dni, clienteState.favoritos)
        // clienteState = clienteRepository.get(clienteState.correo)
        articulosState = if (mostrarFavoritoState) getArticulosFavoritos().toMutableStateList() else getArticulos()

    }

    private fun inicializaTalla(): TallaUiState {
        var tallaSeleccionada = mutableMapOf<TipoTalla, Boolean>()
        tallaSeleccionada[TipoTalla.PEQUEÑA] = false
        tallaSeleccionada[TipoTalla.MEDIANA] = false
        tallaSeleccionada[TipoTalla.GRANDE] = false
        tallaSeleccionada[TipoTalla.XGRANDE] = false
        return TallaUiState(tallaSeleccionada)
    }

    private fun inicializaTalla(tipoTalla: TipoTalla): TallaUiState {
        var talla = inicializaTalla()
        talla.tallaSeleccionada[tipoTalla] = true
        return talla
    }

    private fun buscaArticuloEnPedido(articulo: ArticuloDePedidoUiState): ArticuloDePedidoUiState? {
        return pedidoUiState.articulos.find { a ->
            a.articuloId == articulo.articuloId && a.tamaño == articulo.tamaño
        }
    }

    private fun buscaPosicionArticuloPedido(articulo: ArticuloDePedidoUiState): Int {
        return pedidoUiState.articulos.indexOf(articulo)
    }

    private fun actualizarCifrasPedido() {
        numeroArticulosState = pedidoUiState.articulos.map { a -> a.cantidad }.sum()
        totalCompraState = pedidoUiState.articulos.map { a -> a.cantidad * a.precio }.sum()
    }

    suspend private fun getCliente(login: String) = clienteRepository.get(login)

    private fun Articulo.toArticuloUiState() =
        ArticuloUiState(this.id, this.url, this.precio, this.descripcion, false)

    private fun iniciarNuevoPedido(): PedidoUiState {
        return PedidoUiState(
            dniCliente = clienteState.dni,
            total = 0F,
            fecha = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            articulos = mutableListOf<ArticuloDePedidoUiState>(),
            iniciado = false
        )
    }

    suspend fun inicializaCliente(correo: String?): Cliente {
        if (correo == null) return Cliente("", "", "", "", Direccion("", "", ""), mutableListOf())
        else {
            val c = clienteRepository.get(correo)
            val d = Direccion(c.direccion?.calle, c.direccion?.ciudad, c.direccion?.codigoPostal)
            return Cliente(c.dni, c.correo, c.nombre, c.telefono, d, c.favoritos)
        }
    }

    fun actualizaCliente(correo: String) {
        viewModelScope.launch {
            val c = clienteRepository.get(correo)
            val d =
                Direccion(c.direccion?.calle, c.direccion?.ciudad, c.direccion?.codigoPostal)
            clienteState = Cliente(c.dni, c.correo, c.nombre, c.telefono, d, c.favoritos)
            pedidoUiState = pedidoUiState.copy(dniCliente = c.dni)
            if (clienteState.favoritos.size > 0) articulosState =
                articulosState.checkFavoritos().toMutableStateList()
        }
    }

    fun clearTienda() {
        viewModelScope.launch {
            clienteState = inicializaCliente(null)
            pedidoUiState = iniciarNuevoPedido()
            tallaUiState = inicializaTalla()
            filtroState = ""
            carritoState = false
            numeroArticulosState = 0
            totalCompraState = 0f
            estaFiltrandoState = false
            articuloSeleccionadoState = null
            articulodDePedidoUiState = ArticuloDePedidoUiState()
            mostrarFavoritoState = false
            articulosState = getArticulos()
        }
    }

    private fun MutableList<ArticuloUiState>.checkFavoritos(): MutableList<ArticuloUiState> {
        var listaChecked = mutableListOf<ArticuloUiState>()
        this.forEach {
            if (clienteState != null && clienteState?.favoritos?.contains(it.id)!!) listaChecked.add(
                ArticuloUiState(
                    it.id, it.url, it.precio, it.descripcion, true
                )
            )
            else listaChecked.add(it)
        }
        return listaChecked
    }

    private fun MutableList<Articulo>.toArticulosUiState() =
        this.map { it.toArticuloUiState() }.toMutableList()

    private fun ArticuloDePedidoUiState.toArticuloDePedido() =
        ArticuloDePedido(this.articuloId, this.tamaño, this.cantidad)

    private fun MutableList<ArticuloDePedidoUiState>.toArticuloDePedido(): MutableList<ArticuloDePedido> {
        return this.map { it.toArticuloDePedido() }.toMutableList()
    }

    private fun PedidoUiState.toPedido() = Pedido(
        this.pedidoId,
        this.dniCliente,
        totalCompraState,
        this.fecha,
        this.articulos.toArticuloDePedido()
    )

}

fun ArticuloUiState.toArticuloDePedioUiState() =
    ArticuloDePedidoUiState(this.id, this.url, descripcion, 0, this.precio, 1)


