package com.pmdm.tienda.data.room.cliente

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pmdm.tienda.data.room.ConvertidorDeTipo
import kotlinx.parcelize.Parcelize


@Parcelize
data class Direccion(
    val calle: String?,
    val ciudad: String?,
    @ColumnInfo(name = "codigo_postal")
    val codigoPostal: String?) : Parcelable
{
    override fun toString(): String {
        return "$calle  $ciudad ($codigoPostal)"
    }
}


@Parcelize
@Entity(tableName = "clientes")
data class ClienteEntity(
    @PrimaryKey
    @ColumnInfo(name="dni")
    val dni: String,
    @ColumnInfo(name="correo")
    val correo: String,
    @ColumnInfo(name="nombre")
    val nombre: String,
    @ColumnInfo(name="telefono")
    val telefono: String,
    @ColumnInfo(name="favoritos")
    @TypeConverters(ConvertidorDeTipo::class)
    val favoritos:List<Int>,
    // Marcamos como embebe @Embedded el campo a descomponer
    @Embedded val direccion: Direccion?
    ) : Parcelable

