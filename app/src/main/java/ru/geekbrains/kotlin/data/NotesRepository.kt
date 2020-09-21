package ru.geekbrains.kotlin.data

import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.provider.DataProvider
import ru.geekbrains.kotlin.data.provider.FirestoreProvider


object NotesRepository {
    private val dataProvider: DataProvider = FirestoreProvider()

    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNoteById(id: String) = dataProvider.getNoteById(id)
}

