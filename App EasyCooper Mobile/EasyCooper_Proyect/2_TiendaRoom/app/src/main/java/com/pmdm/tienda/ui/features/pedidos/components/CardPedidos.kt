package com.pmdm.tienda.ui.features.pedidos.components


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.pmdmiesbalmis.components.ui.icons.Filled
import com.pmdm.tienda.ui.features.pedidos.ArticuloConPedido
import com.pmdm.tienda.ui.features.pedidos.PedidoConArticuloUiState
import com.pmdm.tienda.ui.features.tienda.TipoTalla
import com.pmdm.tienda.ui.features.tienda.recursoImagen

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPedidos(
    modifier: Modifier = Modifier,
    pedido: PedidoConArticuloUiState,
    onClickPedido: (PedidoConArticuloUiState) -> Unit
) {

    ElevatedCard(modifier = Modifier.wrapContentSize(),
        onClick = {
            onClickPedido(pedido)

        }) {
        val contexto = LocalContext.current
        val df = DecimalFormat("#.##")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row() {
                Text(
                    text = "Código de pedido: ${pedido.pedidoId}"
                )
                val fecha=
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(pedido.fecha),
                        TimeZone.getTimeZone(ZoneOffset.UTC).toZoneId()
                    ).format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"))
                Spacer(modifier = Modifier.width(70.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    fontSize = 13.sp,
                    text = "${fecha}", fontWeight = FontWeight.Bold)
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(pedido.articulos)
                { item ->
                    val imageResource = if (recursoImagen(item.url) == null) Filled.getPhotoCameraIcon()
                    else painterResource(id = recursoImagen(item.url)!!)
                    Image(
                        modifier = modifier,
                        painter = imageResource,
                        contentDescription = item.url,
                        contentScale = ContentScale.FillHeight,

                        )/*     AsyncImage(
                     modifier = modifier,
                     model = item.url,
                     contentDescription = item.url,
                     contentScale = ContentScale.Crop
                 )*/
                }
            }

            Row(modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = "${pedido.articulos.size} articulos precio total "
                )
                Text(
                    text = "${df.format(pedido.total)}€", fontWeight = FontWeight.Bold
                )
            }


        }
    }
}


@Preview
@Composable
fun CardPedidosTest() {

}