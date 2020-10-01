package ru.geekbrains.kotlin.data.provider


import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.entity.User
import ru.geekbrains.kotlin.data.model.NoteResult

interface DataProvider {
    fun subscribeToAllNotes() : ReceiveChannel<NoteResult>

    suspend fun getCurrentUser() : User?
    suspend fun saveNote(note: Note) : Note
    suspend fun getNoteById(id: String) : Note?
    suspend fun deleteNote(id: String)
}