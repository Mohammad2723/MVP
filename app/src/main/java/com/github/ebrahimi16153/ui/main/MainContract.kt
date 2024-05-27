package com.github.ebrahimi16153.ui.main

import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.util.base.BasePresenter

interface MainContract {

    interface View{
        fun showNotes(notes:List<NoteEntity>)
        fun showError(message:String)
        fun showEmptyContent()

        fun deleteMassage()

    }
    interface Presenter:BasePresenter{
        fun getNotes()
        fun deleteNote(noteEntity: NoteEntity)

        fun filterNote(priority:String)
    }
}