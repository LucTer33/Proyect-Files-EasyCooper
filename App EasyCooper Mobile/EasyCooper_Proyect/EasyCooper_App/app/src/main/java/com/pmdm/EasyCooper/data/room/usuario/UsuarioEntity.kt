package com.pmdm.EasyCooper.data.room.cliente

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    @ColumnInfo(name="login")
    val login: String,
    @ColumnInfo(name="password")
    val password: String
    ) : Parcelable

