package com.github.ebrahimi16153.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.ebrahimi16153.R
import com.github.ebrahimi16153.data.adapter.NoteAdapter
import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.data.repository.home.MainRepository
import com.github.ebrahimi16153.databinding.ActivityMainBinding
import com.github.ebrahimi16153.ui.add.NoteFragment
import com.github.ebrahimi16153.util.Constant
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

    //selected priority
    private var selectedItem = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set ActionBar

        setSupportActionBar(binding.notesToolbar)

        binding.apply {

            //fab click
            fab.setOnClickListener {
                NoteFragment().show(supportFragmentManager, Constant.FRAGMENT_TAG)
            }

            //menu click
            notesToolbar.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.main_menu_Filter ->{
                        showAlertForPriority()
                        return@setOnMenuItemClickListener true
                    }

                    else -> {return@setOnMenuItemClickListener  false}
                }
            }


            // show list or error
            presenter.getNotes()



        }
    }

    override fun showNotes(notes: List<NoteEntity>) {
        binding.noteList.visibility = View.VISIBLE
        binding.emptyLay.visibility = View.GONE

        binding.noteList.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            noteAdapter.setData(data = notes)

            // setOnItemClickListener and onMenuItemClick
            noteAdapter.seOnItemClickListener { noteEntity, state ->


                when(state){
                    //delete
                    Constant.DELETE -> {
                        presenter.deleteNote(noteEntity = noteEntity)
                    }
                    //edite
                    Constant.EDIT ->{

                        val noteFragment = NoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(Constant.BUNDLE_ID,noteEntity.id)
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager,Constant.FRAGMENT_TAG)

                    }
                }
            }
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

    override fun deleteMassage() {
        Snackbar.make(binding.root,"Note deleted :|",Snackbar.LENGTH_SHORT).show()
    }


    private fun showAlertForPriority(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Filter by :")
        val listOFPriority = arrayOf(Constant.ALL,Constant.HIGH,Constant.MEDIUM,Constant.LOW)
        builder.setSingleChoiceItems(listOFPriority,selectedItem){ dialog , item ->

            when(item){
                0 -> {presenter.getNotes()}
               in 1..3 -> {presenter.filterNote(listOFPriority[item])}
            }
            dialog.dismiss()
            selectedItem = item

        }

        val dialog :AlertDialog = builder.create()
        dialog.show()


    }

    // for handel search in topBar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        val search = menu.findItem(R.id.main_menu_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "search..."
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.search(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}