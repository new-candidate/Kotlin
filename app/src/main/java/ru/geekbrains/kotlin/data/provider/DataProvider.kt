package ru.geekbrains.kotlin.data.provider

import androidx.lifecycle.LiveData
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.entity.User
import ru.geekbrains.kotlin.data.model.NoteResult

interface DataProvider {
    fun getCurrentUser() : LiveData<User?>
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
}