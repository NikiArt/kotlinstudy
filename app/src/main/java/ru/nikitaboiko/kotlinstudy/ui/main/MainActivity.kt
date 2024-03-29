package ru.nikitaboiko.kotlinstudy.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.nikitaboiko.kotlinstudy.R

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NotesAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesAdapter()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer { viewState ->
            viewState?.let { adapter.notes = it.notes }
        })
    }
}
