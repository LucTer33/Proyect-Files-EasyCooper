package com.pmdm.tienda.ui.features.pedidos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.tienda.data.PedidoRepository
import com.pmdm.tienda.data.mocks.ArticuloRepository
import com.pmdm.tienda.models.ArticuloDePedido
import com.pmdm.tienda.models.Pedido
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PedidosViewModel @Inject constructor(
    private val pedidoRepository: PedidoRepository,
    private val articuloRepository: ArticuloRepository
) : ViewModel() {

    var dni: String? = null
    var pedidosUiState by mutableStateOf<List<PedidoConArticuloUiState>?>(null)
    var muestraPedidos by mutableStateOf(true)
    var pedidoSeleccionadoUiState: PedidoConArticuloUiState? by mutableStateOf(null)
    val pedidoSeleccionadoEvent: (PedidoConArticuloUiState) -> Unit by mutableStateOf({
        pedidoSeleccionadoUiState = it
        muestraPedidos = false
    })
    val muestraPedidoEvent: (Boolean) -> Unit by mutableStateOf({
        muestraPedidos = it
    })

    init {
        viewModelScope.launch {
            pedidosUiState = getPedidos(dni)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPedidos(dni: String?): List<PedidoConArticuloUiState> {
        return if (dni != null) pedidoRepository.get(dni).toPedidosConArticuloUiState()
        else listOf(PedidoConArticuloUiState(listOf(), -1, 0F, 0L))

    }


    suspend private fun List<Pedido>.toPedidosConArticuloUiState(): List<PedidoConArticuloUiState> {
        return this.map { it.toPedidoConArticuloUiState() }
    }

    suspend private fun Pedido.toPedidoConArticuloUiState() = PedidoConArticuloUiState(
        articulos = this.articulos.toArticulosConPedido(),
        pedidoId = this.pedidoId,
        total = this.total,
        this.fecha
    )

    suspend private fun ArticuloDePedido.toArticuloConPedido(): ArticuloConPedido {

        val articulo = articuloRepository.get(this.articuloId)

        val url = articulo?.url ?: "@drawable/imagen0"
        return ArticuloConPedido(
            url = url,
            cantidad = this.cantidad,
            precio = articulo?.precio ?: 0f,
            tamaño = this.tamaño
        )

    }

    suspend private fun MutableList<ArticuloDePedido>.toArticulosConPedido(): List<ArticuloConPedido> {
        return this.map { it.toArticuloConPedido() }
    }

    fun actualizaPedido(dni: String) {
        viewModelScope.launch {
            pedidosUiState = pedidoRepository.get(dni).toPedidosConArticuloUiState()
        }
    }
}