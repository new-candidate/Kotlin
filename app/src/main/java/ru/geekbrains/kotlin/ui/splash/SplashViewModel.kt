package ru.geekbrains.kotlin.ui.splash

import ru.geekbrains.kotlin.data.NotesRepository
import ru.geekbrains.kotlin.data.errors.NoAuthException
import ru.geekbrains.kotlin.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser(){
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = if(it != null){
                SplashViewState(authenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }

}