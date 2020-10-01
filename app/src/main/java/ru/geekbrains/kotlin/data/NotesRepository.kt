package ru.geekbrains.kotlin.data

import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.provider.DataProvider
import ru.geekbrains.kotlin.data.provider.FirestoreProvider


class NotesRepository(val dataProvider: DataProvider) {
    fun getNotes() = dataProvider.subscribeToAllNotes()
    suspend fun getCurrentUser() = dataProvider.getCurrentUser()
    suspend fun saveNote(note: Note) = dataProvider.saveNote(note)
    suspend fun getNoteById(id: String) = dataProvider.getNoteById(id)
    suspend fun deleteNote(id: String) = dataProvider.deleteNote(id)
}

