package com.github.ebrahimi16153.ui.add

import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.util.base.BasePresenter

interface NoteContract {

    interface View {
        fun closePage()
    }

    interface Presenter : BasePresenter {

        fun saveNote(note: NoteEntity)

    }
}