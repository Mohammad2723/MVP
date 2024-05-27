package com.github.ebrahimi16153.data.repository.home

import com.github.ebrahimi16153.data.database.NoteDao
import com.github.ebrahimi16153.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes() = dao.getAllNote()
    fun deleteNote(noteEntity: NoteEntity) = dao.delete(note = noteEntity)
    fun filterNote(priority:String) = dao.filterByPriority(priority)

}