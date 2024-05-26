package com.github.ebrahimi16153.ui.add

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.ebrahimi16153.R
import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.data.repository.add.AddNoteRepository
import com.github.ebrahimi16153.databinding.FragmentNoteBinding
import com.github.ebrahimi16153.util.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), NoteContract.View {

    // binding
    private lateinit var binding: FragmentNoteBinding

    //repository
    @Inject
    lateinit var repository: AddNoteRepository

    //presenter must be lazy or else it will crash
    // private val presenter by lazy { NotePresenter(repository, this) }

    @Inject
    lateinit var presenter: NotePresenter


    //entity
    @Inject
    lateinit var entity: NoteEntity

    //category
    private lateinit var categoryList: Array<String>
    private lateinit var category: String

    //  Priority
    private lateinit var priorityList: Array<String>
    private lateinit var priority: String

    //noteEntity
    @Inject
    lateinit var noteEntity: NoteEntity

    // noteId
    private var noteId = 0

    //state
    lateinit var state: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getNoteId from Bundle
        val noteId = arguments?.getInt(Constant.BUNDLE_ID) ?: 0

        //state
        state = if (noteId > 0) Constant.EDIT_NOTE else Constant.NEW_NOTE

        // initViews
        binding.apply {

            // dismiss BottomSheet
            closeImg.setOnClickListener {
                this@NoteFragment.dismiss()
            }

            // show detail
            if (state == Constant.EDIT_NOTE) {
                presenter.getNoteById(noteId)
            }
            // spinners
            prioritySpinnerItems()
            categoriesSpinnerItems()



            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val description = descEdt.text.toString()
                val rootView = dialog?.window?.decorView
                if (title.isEmpty() && description.isEmpty()){
                    Snackbar.make(rootView!!,"title and description can't be empty",Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //fill entity
                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.description = description
                noteEntity.priority = priority
                noteEntity.category = category

                when(state){
                    //save
                    Constant.NEW_NOTE -> {

                        presenter.saveNote(noteEntity)

                    }
                    // update
                    Constant.EDIT_NOTE -> {

                        presenter.updateNote(noteEntity)

                    }
                }


            }
        }
    }

    private fun categoriesSpinnerItems() {

        categoryList = arrayOf(Constant.HOME, Constant.WORK, Constant.EDUCATION, Constant.HEALTH)

        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            categoryList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter

        binding.categoriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    category = categoryList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun prioritySpinnerItems() {
        priorityList = arrayOf(Constant.LOW, Constant.MEDIUM, Constant.HIGH)

        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            priorityList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    priority = priorityList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
    }


//    categoryList = arrayOf(Constant.HOME, Constant.WORK, Constant.EDUCATION, Constant.HEALTH)

    override fun showNoteDetail(note: NoteEntity) {
        binding.apply {
            titleEdt.setText(note.title)
            descEdt.setText(note.description)
            categoriesSpinner.setSelection(findIndex(categoryList,note.category))
            prioritySpinner.setSelection(findIndex(priorityList,note.priority))
        }
    }
    override fun showErrorToFindNoteByID() {
        val rootView = dialog?.window?.decorView

        Snackbar.make(
            rootView!!,
            "can not find this Note",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun findIndex(list:Array<String>, value:String): Int {
        val index = list.indexOf(value)
        return index
    }

    override fun closePage() {
        this.dismiss()
    }
}