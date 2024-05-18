package com.github.ebrahimi16153.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.ebrahimi16153.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NoteFragment : BottomSheetDialogFragment() {

    // binding
    private lateinit var binding: FragmentNoteBinding

    //other
    //category
    private lateinit var categoryList: Array<String>
    private lateinit var category: String

    //  Priority
    private lateinit var priorityList: Array<String>
    private lateinit var priority: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

            //spinners
            categoriesSpinnerItems()
            prioritySpinnerItems()


        }

    }

    private fun categoriesSpinnerItems() {

        categoryList = arrayOf("work", "home", "Education", "Health")

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
        priorityList = arrayOf("high", "medium", "low")

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

}