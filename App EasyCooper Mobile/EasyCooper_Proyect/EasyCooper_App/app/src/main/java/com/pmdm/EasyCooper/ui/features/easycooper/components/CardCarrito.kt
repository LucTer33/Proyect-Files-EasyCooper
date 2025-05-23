package com.pmdm.EasyCooper.ui.features.easycooper.components


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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.pmdmiesbalmis.components.ui.icons.Filled
import com.pmdm.EasyCooper.R
import com.pmdm.EasyCooper.ui.features.easycooper.ArticuloDePedidoUiState
import com.pmdm.EasyCooper.ui.features.easycooper.TipoTalla
import com.pmdm.EasyCooper.ui.features.easycooper.recursoImagen
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCarrito(
    modifier: Modifier = Modifier,
    articulo: ArticuloDePedidoUiState,
    onClickMas: () -> Unit,
    onClickMenos: () -> Unit
) {

    ElevatedCard(modifier = Modifier.wrapContentSize(), onClick = {
        //onClickMenos(articulo)
    }) {

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
                contentDescription = articulo.articuloId.toString(),
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
                    text = articulo.descripcion,
                    fontSize = 13.sp,
                    style = TextStyle(lineHeight = 15.sp)
                )
                Text(

                    text = "Talla: ${enumValues<TipoTalla>().firstOrNull { it.ordinal == articulo.tamaño.toInt() }}",
                )
                Spacer(modifier = Modifier.height(10.dp))


                Row() {
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = "${df.format(articulo.precio)}€",
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedIconButton(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(25.dp)
                            .weight(0.1f),
                        onClick = onClickMas,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "uno más",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                        )
                    }
                    Text(
                        modifier = Modifier.weight(0.1f),
                        text = "${articulo.cantidad}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    OutlinedIconButton(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(25.dp)
                            .weight(0.1f),
                        onClick = onClickMenos,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.minus),
                            contentDescription = "uno memos",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                        )
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun CardCarritoTest() {  CardCarrito (
    articulo = ArticuloDePedidoUiState(1, "@drawable/imagen11",  "",2, 120f,1),
    onClickMenos = {}, onClickMas = {}
)

}