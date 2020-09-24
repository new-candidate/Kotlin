package ru.geekbrains.kotlin.data

import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.provider.DataProvider
import ru.geekbrains.kotlin.data.provider.FirestoreProvider


class NotesRepository(val dataProvider: DataProvider) {
    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNoteById(id: String) = dataProvider.getNoteById(id)
    fun deleteNote(id: String) = dataProvider.deleteNote(id)
}

