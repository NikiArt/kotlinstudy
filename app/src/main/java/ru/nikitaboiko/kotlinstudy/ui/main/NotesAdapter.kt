package ru.nikitaboiko.kotlinstudy.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*
import ru.nikitaboiko.kotlinstudy.R
import ru.nikitaboiko.kotlinstudy.data.entities.Note

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    override fun getItemCount() = notes.size
    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(notes[pos])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.note
            setBackgroundColor(note.color)
        }

    }

}