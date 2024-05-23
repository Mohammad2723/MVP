package com.github.ebrahimi16153.ui.main

import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.data.repository.home.MainRepository
import com.github.ebrahimi16153.util.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val repository: MainRepository,
    private val view: MainContract.View
) : MainContract.Presenter, BasePresenterImpl() {
    override fun getNotes() {
        disposable = repository.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it.isEmpty()) {
                    view.showEmptyContent()
                } else {
                    view.showNotes(it)
                }

            }, {

                view.showError(it.message.toString())
            })
    }

    override fun deleteNote(noteEntity: NoteEntity) {
        disposable = repository.deleteNote(noteEntity = noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            view.deleteMassage()
        }
    }
}