package com.github.ebrahimi16153.ui.add

import android.os.Bundle
import android.util.Log
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
    private val presenter by lazy { NotePresenter(repository, this) }

    //entity
    @Inject
    lateinit var entity: NoteEntity

    //category
    private lateinit var categoryList: Array<String>
    private lateinit var category: String

    //  Priority
    private lateinit var priorityList: Array<String>
    private lateinit var priority: String


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

        // initViews
        binding.apply {

            // dismiss BottomSheet
            closeImg.setOnClickListener {
                this@NoteFragment.dismiss()
            }

            // spinners
            prioritySpinnerItems()
            categoriesSpinnerItems()

            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val description = descEdt.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    //fill entity
                    entity.title = title
                    entity.description = description
                    entity.category = category
                    entity.priority = priority

                    //save
                    presenter.saveNote(entity)
                } else {

                    // in BottomSheetDialogFragment must use below code instead binding.root for handel Snack bar
                    val rootView = dialog?.window?.decorView

                    Snackbar.make(
                        rootView!!,
                        "Ops!🥲 title and description can't be empty",
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
        }
    }

    private fun categoriesSpinnerItems() {

        categoryList = arrayOf(Constant.HOME, Constant.WORK, Constant.EDUCATION, Constant.HEALTH)

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
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
            android.R.layout.simple_spinner_item,
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

    override fun closePage() {
        this.dismiss()
    }

}