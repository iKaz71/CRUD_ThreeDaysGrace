package com.kaz.crudtdg.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaz.crudtdg.model.AlbumEntity


@Dao
interface AlbumDao {
    @Insert
    suspend fun insert(album: AlbumEntity)

    @Update
    suspend fun update(album: AlbumEntity)

    @Delete
    suspend fun delete(album: AlbumEntity)

    @Query("SELECT * FROM album_table ORDER BY anio DESC")
    fun getAllAlbums(): LiveData<List<AlbumEntity>>
}