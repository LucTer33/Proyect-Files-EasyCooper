package com.pmdm.EasyCooper.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.pmdm.EasyCooper.data.ClienteRepository
import com.pmdm.EasyCooper.data.PedidoRepository
import com.pmdm.EasyCooper.data.UsuarioRepository
import com.pmdm.EasyCooper.data.mocks.ArticuloRepository
import com.pmdm.EasyCooper.data.room.TiendaDB
import com.pmdm.EasyCooper.data.room.articulo.ArticuloDao
import com.pmdm.EasyCooper.data.room.cliente.ClienteDao
import com.pmdm.EasyCooper.data.room.cliente.UsuarioDao
import com.pmdm.EasyCooper.data.room.pedido.PedidoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideAgendaDatabase(
        @ApplicationContext context: Context
    ): TiendaDB = TiendaDB.getDatabase(context)

    @Provides
    @Singleton
    fun provideArticuloDao(
        db: TiendaDB
    ): ArticuloDao = db.articuloDao()

    @Provides
    @Singleton
    fun provideArticuloRepository(
        articuloDao: ArticuloDao
    ): ArticuloRepository =
        ArticuloRepository(articuloDao)

    @Provides
    @Singleton
    fun provideUsuarioDao(
        db: TiendaDB
    ): UsuarioDao = db.usuarioDao()

    @Provides
    @Singleton
    fun provideUsuarioRepository(
        usuarioDao: UsuarioDao
    ): UsuarioRepository =
        UsuarioRepository(usuarioDao)

    @Provides
    @Singleton
    fun provideClienteDao(
        db: TiendaDB
    ): ClienteDao = db.clienteDao()

    @Provides
    @Singleton
    fun provideClienteRepository(
        clienteDao: ClienteDao
    ): ClienteRepository =
        ClienteRepository(clienteDao)


    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun providePedidoRepository(
        pedidoDao: PedidoDao
    ): PedidoRepository =
        PedidoRepository(pedidoDao)

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun providePedidoDao(
        db: TiendaDB
    ): PedidoDao = db.pedidoDao()

}