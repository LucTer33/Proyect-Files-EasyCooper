package com.pmdm.tienda.ui.features.pedidos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.tienda.ui.features.pedidos.components.Pedido
import com.pmdm.tienda.ui.features.pedidos.components.Pedidos


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosScreen(
    pedidos: List<PedidoConArticuloUiState>,
    pedidoSeleccionado: PedidoConArticuloUiState?,
    onClickPedido: (PedidoConArticuloUiState) -> Unit,
    onClickMuestraPedido: (Boolean) -> Unit,
    muestraPedidos: Boolean,
) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                modifier = Modifier.height(20.dp),
                navigationIcon = {
                    if (!muestraPedidos) {
                        IconButton(onClick = { onClickMuestraPedido(!muestraPedidos) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                   }
                    else{}
                },
                title = { Text(text = "") },
            )
        })
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
        )
        {
            if (muestraPedidos) Pedidos(pedidos, onClickPedido)
            else if (pedidoSeleccionado != null) Pedido(pedido = pedidoSeleccionado)

        }
    }
}

