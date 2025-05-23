package com.pmdm.EasyCooper.ui.features.easycooper.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.EasyCooper.ui.features.easycooper.PedidoUiState
import com.pmdm.EasyCooper.ui.features.easycooper.TiendaEvent

@Composable
fun Carrito(
    pedido: PedidoUiState,
    onTiendaEvent: (TiendaEvent) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(pedido.articulos) { item ->
            CardCarrito(
                articulo = item,
                modifier = Modifier.size(80.dp),
                onClickMas = {
                    onTiendaEvent(TiendaEvent.OnClickMas(item))
                },
                onClickMenos = {
                    onTiendaEvent(TiendaEvent.OnClickMenos(item))
                }
            )
        }
    }
}