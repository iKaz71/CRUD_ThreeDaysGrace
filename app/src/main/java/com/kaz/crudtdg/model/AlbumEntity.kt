package com.kaz.crudtdg.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "album_table")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val anio: Int,
    val tipo: String,
    val favorito: Boolean = false
) : Serializable
