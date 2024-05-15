package com.github.ebrahimi16153.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.ebrahimi16153.data.model.NoteEntity


@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDataBase:RoomDatabase() {
    abstract val dao:NoteDao

}