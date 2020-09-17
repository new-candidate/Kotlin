package ru.geekbrains.kotlin.ui.note

import androidx.lifecycle.Observer
import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.model.NoteResult
import ru.geekbrains.kotlin.ui.base.BaseViewModel

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        val noteLiveData = NotesRepository.getNoteById(noteId)

        val observer = object : Observer<NoteResult> {

            override fun onChanged(result: NoteResult?) {
                noteLiveData.removeObserver(this)
                when (result) {
                    is NoteResult.Succes<*> -> viewStateLiveData.value =
                        NoteViewState(note = result.data as? Note)
                    is NoteResult.Error -> viewStateLiveData.value =
                        NoteViewState(error = result.error)
                }
            }
        }
        NotesRepository.getNoteById(noteId).observeForever {
            noteLiveData.observeForever (observer)
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }

}