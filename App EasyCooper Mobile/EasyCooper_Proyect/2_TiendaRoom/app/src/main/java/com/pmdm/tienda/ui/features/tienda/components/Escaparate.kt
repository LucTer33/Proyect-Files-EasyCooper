package com.pmdm.tienda.ui.features.tienda.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.tienda.ui.features.tienda.ArticuloUiState
import com.pmdm.tienda.ui.features.tienda.TallaEvent
import com.pmdm.tienda.ui.features.tienda.TallaUiState
import com.pmdm.tienda.ui.features.tienda.TiendaEvent

@Composable

fun Escaparate(
    articulos: List<ArticuloUiState>,
    articuloSeleccionado: ArticuloUiState?,
    tallaUiState: TallaUiState,
    onTallaEvent: (TallaEvent) -> Unit,
    onTiendaEvent: (TiendaEvent) -> Unit,
) {


    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(all = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = articulos,
            key = { it.id }) { item ->
            var favorito by remember {
                mutableStateOf(item.favorito)
            }
            CardArticulo(
                articulo = item,
                favorito = favorito,
                modifier = Modifier.size(150.dp),
                onClickFavorito = {
                    onTiendaEvent(TiendaEvent.OnClickFavorito(item))
                    favorito = it
                },
                onClickArticulo = {
                    onTiendaEvent(TiendaEvent.OnClickArticulo(it))
                }
            )
        }
    }
    if (articuloSeleccionado != null) {
        DialogoCompra(
            articulo = articuloSeleccionado,
            onClickAñadirCesta = {

                onTiendaEvent(TiendaEvent.OnClickAñadirCesta(it))
            },
            talla = tallaUiState,
            onDismissRequest = {
                onTiendaEvent(TiendaEvent.OnDismissDialog)
            },
            onClickTalla = onTallaEvent
        )
    }

}