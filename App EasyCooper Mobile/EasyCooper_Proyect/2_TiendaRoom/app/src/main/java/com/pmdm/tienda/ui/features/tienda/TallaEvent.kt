package com.pmdm.tienda.ui.features.tienda

sealed interface TallaEvent{
    data class OnClickPequeña(val talla: TallaUiState): TallaEvent
    data class OnClickMediana(val talla: TallaUiState): TallaEvent
    data class OnClickGrande(val talla: TallaUiState): TallaEvent
    data class OnClickXGrande(val talla: TallaUiState): TallaEvent
}
