package com.pmdm.EasyCooper.ui.features.easycooper.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.pmdm.EasyCooper.models.Cliente
import com.pmdm.EasyCooper.models.Direccion
import com.pmdm.EasyCooper.ui.theme.LoginTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    filtro: String,
    estaFiltrando: Boolean,
    totalCompra: Float,
    carrito: Boolean,
    clienteUiState: Cliente,
    onEstaFiltrandoChange: (Boolean) -> Unit,
    onFiltroChange: (String) -> Unit,
    onClickFiltrar: (String) -> Unit,
    onClickUsuario: (Int) -> Unit,
    onClickCompar: () -> Unit,
    onClickCasa: () -> Unit,
) {
    val opcionesMenuUsuario = listOf(
        "Editar datos",
        "Cerrar sesión"
    )

    var mostrarMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {},
        actions = {

            if (carrito) {
                val df = DecimalFormat("#.##")
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(0.5f)
                ) {
                    Text(
                        modifier = Modifier.weight(0.3f),
                        text = "Total: ${df.format(totalCompra)}€"
                    )
                    IconButton(
                        modifier = Modifier.weight(0.2f),
                        onClick = { onEstaFiltrandoChange(true) })
                    {
                        OutlinedButton(onClick = onClickCompar) {
                            Text(text = "Comprar")
                        }
                    }
                }
            } else if (!estaFiltrando) {
                IconButton(onClick = { onEstaFiltrandoChange(!estaFiltrando) }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Filtrar"
                    )
                }
            } else {
                SearchBar(
                    modifier = Modifier
                        .weight(0.9f)
                        .size(height = 65.dp, width = 0.dp),
                    query = filtro,
                    placeholder = {
                        Text(text = "Filtrar")
                    },
                    onQueryChange = {
                        onFiltroChange(it)
                        onClickFiltrar(filtro)
                    },
                    onSearch = { onClickFiltrar(filtro) },
                    active = estaFiltrando,
                    onActiveChange = onEstaFiltrandoChange,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                onFiltroChange("")
                            },
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Filtrar",
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                onEstaFiltrandoChange(!estaFiltrando)
                                onClickCasa()
                            },
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Filtrar",
                        )
                    },
                )
                {
                }
            }

            IconButton(onClick = { mostrarMenu=true })
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Icon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Datos Personales",
                    )
                    Text(
                        text = clienteUiState.nombre.split(" ")[0],
                        style = MaterialTheme.typography.labelSmall
                    )
                    DropdownMenu(
                        expanded = mostrarMenu,
                        onDismissRequest = { mostrarMenu = false }) {
                        for (o in opcionesMenuUsuario.indices) {
                            DropdownMenuItem(
                                text = { Text(opcionesMenuUsuario[o]) },
                                onClick = {
                                    onClickUsuario(o)
                                    mostrarMenu = false
                                })
                        }
                    }
                }
            }

        },
    )
}



@Preview
@Composable
fun BarraSuperiorTest() {
    val direccionUiState = Direccion("C\\ Las partidas", "Alicante", "03001")
    val clienteUiState = Cliente(
        "21231234Y",
        "ana@gmail.com",
        "Ana García",
        "627541665",
        direccionUiState,
        emptyList<Int>().toMutableList()
    )
    LoginTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            BarraSuperior("", true, 126f, false, clienteUiState, {}, {}, {}, {}, {}, {})
        }
    }
}