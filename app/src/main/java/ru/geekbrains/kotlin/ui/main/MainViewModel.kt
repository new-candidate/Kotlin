package ru.geekbrains.kotlin.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.model.NoteResult
import ru.geekbrains.kotlin.ui.base.BaseViewModel

class MainViewModel(notesRepository: NotesRepository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: return@Observer
        when(result){
            is NoteResult.Success<*> ->  viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repositoryNotes = notesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever (notesObserver)
    }

    @VisibleForTesting
    override public fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }
}