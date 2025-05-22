package com.pmdm.tienda.ui.features.pedidos.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
import com.pmdm.tienda.models.Articulo
import com.pmdm.tienda.ui.features.pedidos.ArticuloConPedido
import com.pmdm.tienda.ui.features.tienda.TipoTalla
import com.pmdm.tienda.ui.features.tienda.components.CardArticulo
import com.pmdm.tienda.ui.features.tienda.recursoImagen

import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPedido(
    modifier: Modifier = Modifier,
    articulo: ArticuloConPedido,
) {

    ElevatedCard(modifier = Modifier.wrapContentSize()) {

        val df = DecimalFormat("#.##")

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp),
        ) {
            val imageResource = if (recursoImagen(articulo.url) == null) Filled.getPhotoCameraIcon()
            else painterResource(id = recursoImagen(articulo.url)!!)

            Image(
                modifier = modifier,
                painter =  imageResource,
                contentDescription = articulo.url,
                contentScale = ContentScale.Crop
            )/*     AsyncImage(
                     modifier = modifier,
                     model = articulo.url,
                     contentDescription = articulo.descripcion,
                     contentScale = ContentScale.Crop
                 )*/

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)) {

                Text(

                    text = "Talla: ${enumValues<TipoTalla>().firstOrNull { it.ordinal == articulo.tamaño.toInt() }}",
                )
                Spacer(modifier = Modifier.height(10.dp))


                Row() {
                    Text(
                        modifier = Modifier.weight(0.3f),
                        text = "${df.format(articulo.precio)}€",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.weight(0.2f),
                        text = "Cantidad:  ${articulo.cantidad}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                }
            }
        }
    }
}

