package ru.geekbrains.kotlin.ui.note

import androidx.lifecycle.Observer
import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.model.NoteResult
import ru.geekbrains.kotlin.ui.base.BaseViewModel

class NoteViewModel(val notesRepository: NotesRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        notesRepository.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> {
                    pendingNote = result.data as? Note
                    viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = pendingNote))
                }
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesRepository.deleteNote(it.id).observeForever { result ->
                result ?: return@observeForever
                pendingNote = null
                when (result) {
                    is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }

}