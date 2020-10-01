package ru.geekbrains.kotlin.ui.splash

import kotlinx.coroutines.launch
import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.errors.NoAuthException
import ru.geekbrains.kotlin.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthException())
    }

}