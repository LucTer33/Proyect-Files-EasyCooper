package com.pmdm.EasyCooper.data.room.cliente

import androidx.room.*


@Dao
interface UsuarioDao {

    @Query("SELECT * FROM USUARIOS")
    suspend fun get(): List<UsuarioEntity>

    @Query("SELECT * FROM usuarios WHERE login IN (:login)")
    suspend fun get(login:String): UsuarioEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioEntity)

    @Update
    suspend fun update(usuario: UsuarioEntity)

}