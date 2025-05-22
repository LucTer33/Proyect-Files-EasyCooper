package com.pmdm.tienda.ui.features.pedidos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.tienda.ui.features.pedidos.PedidoConArticuloUiState


@Composable
fun Pedido(
    pedido: PedidoConArticuloUiState,
    //onTiendaEvent: (TiendaEvent) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(pedido.articulos.size) { item ->
            CardPedido(
                articulo =pedido.articulos[item],
                modifier = Modifier.size(80.dp),

        )}
    }
}