package com.pmdm.tienda.ui.features.tienda

enum class TipoTalla(val tipo: String) {
    PEQUEÑA("S"), MEDIANA("M"), GRANDE("L"), XGRANDE("XL"), NOTALLA("Not")
}
data class TallaUiState(val tallaSeleccionada: MutableMap<TipoTalla,Boolean>)
