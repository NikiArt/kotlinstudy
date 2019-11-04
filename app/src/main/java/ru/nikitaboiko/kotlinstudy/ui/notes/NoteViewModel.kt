package ru.nikitaboiko.kotlinstudy.ui.notes

import androidx.lifecycle.ViewModel
import ru.nikitaboiko.kotlinstudy.data.NotesRepository
import ru.nikitaboiko.kotlinstudy.data.entities.Note

class NoteViewModel : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}