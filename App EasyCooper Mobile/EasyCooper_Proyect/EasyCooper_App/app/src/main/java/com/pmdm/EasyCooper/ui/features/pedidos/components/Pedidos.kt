package com.pmdm.EasyCooper.ui.features.pedidos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.EasyCooper.ui.features.pedidos.PedidoConArticuloUiState


@Composable
fun Pedidos(
    pedidos: List<PedidoConArticuloUiState>,
    onClickPedido: (PedidoConArticuloUiState) -> Unit,
) {
    LazyColumn(

        contentPadding = PaddingValues(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(pedidos) {
            CardPedidos(
                pedido = it,
                onClickPedido=onClickPedido,
                modifier = Modifier.size(80.dp),
            )
        }

    }
}