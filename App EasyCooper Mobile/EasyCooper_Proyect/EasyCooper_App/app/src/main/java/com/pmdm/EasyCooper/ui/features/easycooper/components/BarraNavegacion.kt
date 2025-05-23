package com.pmdm.EasyCooper.ui.features.easycooper.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.EasyCooper.R


@Composable
fun BarraNavegacion(
    numeroArticulos: Int,
    onClickFavoritos: () -> Unit,
    onClickCarrito: () -> Unit,
    onClickCasa: () -> Unit,
    onClickPedidos:()->Unit
) {

    data class OpcionMenu(val descripcion: String, val icono: ImageVector, val accion: () -> Unit)

    val items = listOf(
        OpcionMenu("Volver", Icons.Filled.Home, onClickCasa),
        OpcionMenu("Favoritos", Icons.Filled.Favorite, onClickFavoritos),
        OpcionMenu("Pedidos", ImageVector.vectorResource(R.drawable.shopping), onClickPedidos),
        OpcionMenu("Carrito", Icons.Filled.ShoppingCart, onClickCarrito),
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(modifier = Modifier.height(35.dp)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = {
                if (item.icono == Icons.Filled.ShoppingCart)
                    CarritoCompra(numeroArticulos)
                else Icon(
                    imageVector = item.icono,
                    contentDescription = item.descripcion,
                )
            }, selected = selectedItem == index, onClick = {
                item.accion()
                selectedItem = index
            })
        }
    }
}

@Composable
fun CarritoCompra(numeroArticulos: Int) {
    val colorPrimary = MaterialTheme.colorScheme.primary
    Box(modifier = Modifier.wrapContentSize()) {
        Icon(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.TopStart),
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Carrito",
        )
        Text(text = numeroArticulos.toString(),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .drawBehind {
                    drawCircle(
                        color = colorPrimary, radius = 24f
                    )
                })
    }
}

@Preview
@Composable
fun bottomAppBarTest() {
    BarraNavegacion( 15, {}, {}, {},{})
}