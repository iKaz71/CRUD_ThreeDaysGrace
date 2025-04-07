package com.kaz.crudtdg.data

import androidx.lifecycle.LiveData
import com.kaz.crudtdg.model.AlbumEntity

class AlbumRepository(private val albumDao: AlbumDao) {
    val allAlbums: LiveData<List<AlbumEntity>> = albumDao.getAllAlbums()

    suspend fun insert(album: AlbumEntity) = albumDao.insert(album)
    suspend fun update(album: AlbumEntity) = albumDao.update(album)
    suspend fun delete(album: AlbumEntity) = albumDao.delete(album)
}
