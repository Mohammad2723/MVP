package com.github.ebrahimi16153.ui.main

import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.util.base.BasePresenter

interface MainContract {

    interface View{
        fun showNotes(notes:List<NoteEntity>)
        fun showError(message:String)

        fun showEmptyContent()
    }
    interface Presenter:BasePresenter{
        fun getNotes()
    }
}