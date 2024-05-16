package com.github.ebrahimi16153.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ebrahimi16153.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NoteFragment : BottomSheetDialogFragment() {

    // binding
    private lateinit var binding: FragmentNoteBinding

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

              closeImg.setOnClickListener {
                  this@NoteFragment.dismiss()
              }


        }

    }

}