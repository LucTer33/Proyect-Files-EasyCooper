package com.pmdm.tienda.data.room.articulo

import androidx.room.*


@Dao
interface ArticuloDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articulo : ArticuloEntity)

    @Delete
    suspend fun delete(articulo : ArticuloEntity)

    @Update
    suspend fun update(articulo : ArticuloEntity)

    @Query("SELECT * FROM articulos WHERE id=:id")
    suspend fun get(id:Int): ArticuloEntity
    @Query("SELECT * FROM articulos WHERE descripcion  LIKE '%' || :filtro || '%'")
    suspend fun get(filtro:String):  List<ArticuloEntity>
    @Query("SELECT * FROM articulos")
    suspend fun get(): List<ArticuloEntity>

}





