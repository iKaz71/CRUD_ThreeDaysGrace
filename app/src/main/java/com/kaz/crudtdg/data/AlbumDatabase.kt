package com.kaz.crudtdg.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaz.crudtdg.model.AlbumEntity


@Database(entities = [AlbumEntity::class], version = 2, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var INSTANCE: AlbumDatabase? = null

        fun getDatabase(context: Context): AlbumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlbumDatabase::class.java,
                    "album_database"
                )
                    .fallbackToDestructiveMigration()//clean DB por si falla
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
