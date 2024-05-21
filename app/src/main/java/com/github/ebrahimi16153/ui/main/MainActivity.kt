package com.github.ebrahimi16153.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.ebrahimi16153.data.adapter.NoteAdapter
import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.data.repository.home.MainRepository
import com.github.ebrahimi16153.databinding.ActivityMainBinding
import com.github.ebrahimi16153.ui.add.NoteFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    //binding
    private lateinit var binding: ActivityMainBinding

    //adapter
    @Inject
    lateinit var noteAdapter: NoteAdapter

    //repository
    @Inject
    lateinit var repository: MainRepository

    //presenter
    @Inject lateinit var  presenter: MainPresenter
//    private val presenter by lazy { MainPresenter(repository, this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            //fab click
            fab.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }

            // show list or error
            presenter.getNotes()


        }
    }

    override fun showNotes(notes: List<NoteEntity>) {
        binding.noteList.isVisible = true
        binding.emptyLay.isVisible = false

        binding.noteList.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            noteAdapter.setData(data = notes)
        }
    }

    override fun showError(message: String) {
        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
    }

    override fun showEmptyContent() {
        binding.apply {

            noteList.isVisible = false
            emptyLay.isVisible = true


        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()

    }
}