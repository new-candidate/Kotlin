package ru.geekbrains.kotlin.data.model

sealed class NoteResult {
    data class Succes<out T>(val data: T) : NoteResult()
    data class Error(val error: Throwable) : NoteResult()
}