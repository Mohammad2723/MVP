package com.github.ebrahimi16153.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ebrahimi16153.databinding.ActivityMainBinding
import com.github.ebrahimi16153.ui.add.NoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            fab.setOnClickListener {
                NoteFragment().show(supportFragmentManager,NoteFragment().tag)
            }
        }
    }
}