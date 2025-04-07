package com.kaz.crudtdg.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.kaz.crudtdg.R
import com.kaz.crudtdg.data.AlbumDatabase
import com.kaz.crudtdg.data.AlbumRepository
import com.kaz.crudtdg.model.AlbumEntity
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository
    private val context = application.applicationContext

    val allAlbums: LiveData<List<AlbumEntity>>

    init {
        val dao = AlbumDatabase.getDatabase(application).albumDao()
        repository = AlbumRepository(dao)
        allAlbums = repository.allAlbums.map { list ->
            list.sortedWith(compareByDescending<AlbumEntity> { it.favorito }.thenBy { it.titulo })
        }
    }

    fun insert(album: AlbumEntity) = viewModelScope.launch {
        repository.insert(album)
        Toast.makeText(context, context.getString(R.string.toast_album_agregado), Toast.LENGTH_SHORT).show()
    }

    fun update(album: AlbumEntity) = viewModelScope.launch {
        repository.update(album)
        Toast.makeText(context, context.getString(R.string.toast_album_actualizado), Toast.LENGTH_SHORT).show()
    }

    fun delete(album: AlbumEntity) = viewModelScope.launch {
        repository.delete(album)
        Toast.makeText(context, context.getString(R.string.toast_album_eliminado), Toast.LENGTH_SHORT).show()
    }
}
