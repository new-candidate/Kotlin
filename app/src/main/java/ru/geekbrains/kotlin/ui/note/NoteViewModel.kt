package ru.geekbrains.kotlin.ui.note

import androidx.lifecycle.ViewModel
import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.entity.Note

class NoteViewModel : ViewModel() {
    private var pendingNote: Note? = null
    fun save (note: Note) {
        pendingNote = note
    }
    override fun onCleared() {
        pendingNote?.let{
            NotesRepository.saveNote(it)
        }
    }
}