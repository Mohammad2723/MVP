package com.github.ebrahimi16153.data.repository.add

import com.github.ebrahimi16153.data.database.NoteDao
import com.github.ebrahimi16153.data.model.NoteEntity
import javax.inject.Inject

class AddNoteRepository @Inject constructor(private val dao: NoteDao) {

    fun addNote(note: NoteEntity) = dao.insert(note)
    fun getNoteById(id:Int) = dao.getNoteById(id)

    fun update(note: NoteEntity) = dao.update(note = note)
}