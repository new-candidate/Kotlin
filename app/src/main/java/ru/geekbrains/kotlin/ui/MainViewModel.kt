package ru.geekbrains.kotlin.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.kotlin.data.NotesRepository

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        viewStateLiveData.value = MainViewState(NotesRepository.getNotes())
    }

    fun getViewState(): LiveData<MainViewState> = viewStateLiveData



//    private val viewStateLiveData = MutableLiveData<MainViewState>()
//
//    init {
//        viewStateLiveData.value = MainViewState(NotesRepository.getNotes())
//    }
//    fun getViewState(): LiveData<String> = viewStateLiveData

}