package com.github.ebrahimi16153.ui.add

import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.data.repository.add.AddNoteRepository
import com.github.ebrahimi16153.util.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotePresenter @Inject constructor(
    private val addNoteRepository: AddNoteRepository,
    private val view: NoteContract.View
) :
    NoteContract.Presenter, BasePresenterImpl() {
    override fun saveNote(note: NoteEntity) {

        disposable = addNoteRepository.addNote(note = note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                view.closePage()
            }


    }

    override fun getNoteById(id: Int) {
        disposable = addNoteRepository.getNoteById(id = id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                view.showNoteDetail(note = it)
            }, {
                view.showErrorToFindNoteByID()
            })
    }

    override fun updateNote(note: NoteEntity) {
        disposable = addNoteRepository.update(note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.closePage()
        }
    }


}