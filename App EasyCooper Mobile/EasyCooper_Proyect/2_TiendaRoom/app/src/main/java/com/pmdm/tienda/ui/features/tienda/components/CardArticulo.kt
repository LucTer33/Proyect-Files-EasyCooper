package com.pmdm.tienda.ui.features.tienda.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.ui.icons.Filled
import com.pmdm.tienda.ui.features.tienda.ArticuloUiState
import com.pmdm.tienda.ui.features.tienda.recursoImagen
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardArticulo(
    modifier: Modifier = Modifier,
    articulo: ArticuloUiState,
    favorito: Boolean,
    onClickFavorito: (Boolean) -> Unit,
    onClickArticulo: (ArticuloUiState) -> Unit
) {

    ElevatedCard(modifier = Modifier.wrapContentSize(),
        onClick = { onClickArticulo(articulo) }) {

        val df = DecimalFormat("#.##")

        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageResource = if (recursoImagen(articulo.url) == null) Filled.getPhotoCameraIcon()
            else painterResource(id = recursoImagen(articulo.url)!!)

            Image(
                modifier = modifier,
                painter = imageResource,
                contentDescription = articulo.descripcion,
                contentScale = ContentScale.Crop
            )/*     AsyncImage(
                     modifier = modifier,
                     model = articulo.url,
                     contentDescription = articulo.descripcion,
                     contentScale = ContentScale.Crop
                 )*/

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = "${df.format(articulo.precio)}€",
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable {
                            onClickFavorito(!favorito)
                        }, painter = rememberVectorPainter(
                        image = if (articulo.favorito
                        ) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                    ), contentDescription = "Favorito"
                )
            }
        }
    }
}

@Preview
@Composable
fun CardArticuloTest() {/*  CardArticulo(
          articulo = Articulo(1, "https://loremflickr.com//400/400//?lock=$3", 25f, ""),
          //  articulo = Articulo(1, "@drawable/imagen11", 25f, ""),
          modifier = Modifier.size(150.dp), mutableListOf<Int>{0,3}, onClickFavorite = {}
      )*/

}