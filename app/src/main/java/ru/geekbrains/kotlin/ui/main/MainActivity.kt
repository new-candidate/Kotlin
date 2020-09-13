package ru.geekbrains.kotlin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.kotlin.R
import ru.geekbrains.kotlin.ui.note.NoteActivity

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter() {
            NoteActivity.start(this, it)
        }

        rv_notes.adapter = adapter
        viewModel.getViewState().observe(this, Observer { value ->
            value?.let {
                adapter.notes = it.notes
            }
        })

        fab.setOnClickListener{
            NoteActivity.start(this)
        }
    }
}