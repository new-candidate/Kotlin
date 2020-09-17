package ru.geekbrains.kotlin.ui.main

import androidx.lifecycle.Observer
import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.model.NoteResult
import ru.geekbrains.kotlin.ui.base.BaseViewModel

class MainViewModel() : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: return@Observer
        when(result){
            is NoteResult.Succes<*> ->  viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever (notesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }
}