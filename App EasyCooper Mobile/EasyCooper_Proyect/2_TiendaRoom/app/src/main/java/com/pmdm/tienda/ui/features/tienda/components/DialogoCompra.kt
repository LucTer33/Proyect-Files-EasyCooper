package com.pmdm.tienda.ui.features.tienda.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.ui.composables.FilterChipWithIcon
import com.github.pmdmiesbalmis.components.ui.icons.Filled
import com.pmdm.tienda.ui.features.tienda.ArticuloDePedidoUiState
import com.pmdm.tienda.ui.features.tienda.ArticuloUiState
import com.pmdm.tienda.ui.features.tienda.TallaEvent
import com.pmdm.tienda.ui.features.tienda.TallaUiState
import com.pmdm.tienda.ui.features.tienda.TipoTalla
import com.pmdm.tienda.ui.features.tienda.recursoImagen
import com.pmdm.tienda.ui.features.tienda.toArticuloDePedioUiState
import kotlin.random.Random

@Composable
fun DialogoCompra(
    articulo: ArticuloUiState,
    talla: TallaUiState,
    onClickAñadirCesta: (ArticuloDePedidoUiState) -> Unit,
    onDismissRequest: () -> Unit,
    onClickTalla: (TallaEvent) -> Unit,
) {

    val contexto= LocalContext.current

    data class FilterChipGroupUiState(
        val label: String = "",
        val selected: Boolean = false,
        val onClick: () -> Unit = {}
    )


    var tallaSeleccionadaState by remember {
        mutableStateOf(false)
    }

    var contenido= mutableListOf(
        FilterChipGroupUiState(
            label = TipoTalla.PEQUEÑA.tipo,
            selected =talla.tallaSeleccionada[TipoTalla.PEQUEÑA]!!,
            onClick = {
                onClickTalla(TallaEvent.OnClickPequeña(talla))

            }
        ),
        FilterChipGroupUiState(
            label = TipoTalla.MEDIANA.tipo,
            selected =talla.tallaSeleccionada[TipoTalla.MEDIANA]!!,
            onClick = { onClickTalla(TallaEvent.OnClickMediana(talla)) }
        ),
        FilterChipGroupUiState(
            label = TipoTalla.GRANDE.tipo,
            selected = talla.tallaSeleccionada[TipoTalla.GRANDE]!!,
            onClick = { onClickTalla(TallaEvent.OnClickGrande(talla)) }
        ),
        FilterChipGroupUiState(
            label = TipoTalla.XGRANDE.tipo,
            selected = talla.tallaSeleccionada[TipoTalla.XGRANDE]!!,
            onClick = { onClickTalla(TallaEvent.OnClickXGrande(talla)) }
        )
    )

    var contenidoAuxiliar =contenido.toMutableList()

    AlertDialog(
        modifier = Modifier.height(500.dp),
        onDismissRequest = onDismissRequest,
        text = {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val imageResource = if (recursoImagen(articulo.url) == null) Filled.getPhotoCameraIcon()
                else painterResource(id = recursoImagen(articulo.url)!!)

                Image(
                    modifier = Modifier.size(310.dp),
                    painter = imageResource,
                    contentDescription = articulo.descripcion,
                    contentScale = ContentScale.Crop
                )
                /*     AsyncImage(
                     modifier = modifier,
                     model = articulo.url,
                     contentDescription = articulo.descripcion,
                     contentScale = ContentScale.Crop
                 )*/

                Text("Elige la talla del vestido", color = Color.Black)
                LazyRow(
                    contentPadding = PaddingValues(all = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                )
                {
                    items(contenido.size)
                    {
                       FilterChipWithIcon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textoState = contenido[it].label,
                            seleccionadoState = contenido[it].selected,
                            onClick = {
                                tallaSeleccionadaState=!tallaSeleccionadaState
                                contenido[it].onClick()
                            }
                        )
                    }

                }
            }

        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (tallaSeleccionadaState) {
                        onClickAñadirCesta(articulo.toArticuloDePedioUiState())
                        onDismissRequest()

                    } else
                        Toast.makeText(
                            contexto,
                            "Debes seleccionar una talla\nantes de añadir al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            ) {
                Text("Añadir a la cesta")
            }
        },
    )


}

@Preview
@Composable
fun dialogoCompraTest() {
    var tallaSeleccionada = mutableMapOf<TipoTalla, Boolean>()
    val urlBase = "@drawable/"
    val articulo =
        ArticuloUiState(1, "${urlBase}imagen$1", Random.nextFloat() * (100) + 200, "", false)
    DialogoCompra(articulo = articulo, TallaUiState(tallaSeleccionada), {}, {}, {})
}