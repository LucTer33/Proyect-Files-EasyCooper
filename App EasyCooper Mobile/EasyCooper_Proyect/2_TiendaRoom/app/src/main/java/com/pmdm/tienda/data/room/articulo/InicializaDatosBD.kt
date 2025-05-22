package com.pmdm.tienda.data.room.articulo

import kotlin.random.Random

suspend fun inicializaArticulos(proveedorArticulo: ArticuloDao) {
    val TOPE_PRECIO = 350
    val BASE_PRECIO = 100
    val urlBase = "@drawable/"
    val descripcion= listOf(
        "Elegante vestido con detalles dorados y complementos en negro-dorado",
        "Vestido elegante color verde turquesa con altos tacones en turquesa",
        "Vestido primaveral en color verde para una tarde de paseo",
        "Vestido de noche con corpiño de pedrería color verde turquesa oscuro con pulsera a juego",
        "Vestido azul rey para noche elegante, con zapatos y pendientes del mismo color ",
        "Vestido corto estilo bohemio color morado índigo",
        "Vestido  elegante morado purpura con cinturón y zapatos tacón muy alto",
        "Vestido largo elegante negro con detalles morados purpura",
        "Vestido de noche negro largo con detalles en negro y dorado",
        "Vestido corto negro con estilo bohemio para tarde o noche",
        "Vestido de coctel color crema con cinturón",
        "Vestido de coctel color crema marfil con bolso y zapatos turquesa claro",
        "Elegante vestido de dia en rosa crepé y falda plisada con preciosos zapatos a juego",
        "Vestido de día para paseo, de color naranja con falda jaspeada en tonos crema",
        "Vestido de encaje naranja para coctel con detares en dorado",
        "Elegante vestido romántico para cena o fiesta de color escarlata y con preciosa sobre tela de encaje",
        "Vestido primaveral blanco con estampado de flores en rojo y con vonitos zapatos en el mismo color",
        "Vestido de encaje en rojo y con detalles en azul para coctel",
        "Vestido primaveral palabra de honor con tela multicolor en rojos y verdes para paseos de día",
        "Elegante vestido estilo años veinte con fondo en blanco y detalles en dorado y negro"
    )
    if(proveedorArticulo.get().size==0) {
        for (i in 1..20) {
            proveedorArticulo.insert(
                ArticuloEntity(
                    id = i,
                  //  url = "${urlBase}imagen$i",
                    url = "imagen$i",
                    precio = Random.nextFloat() * (TOPE_PRECIO - BASE_PRECIO) + BASE_PRECIO,
                    descripcion = descripcion[i - 1]
                )
            )
        }
    }
}