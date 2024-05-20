package com.github.ebrahimi16153.data.repository.home

import com.github.ebrahimi16153.data.database.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes() = dao.getAllNote()

}