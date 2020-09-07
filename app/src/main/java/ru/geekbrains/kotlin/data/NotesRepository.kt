package ru.geekbrains.kotlin.data

import ru.geekbrains.kotlin.data.entity.Note

object NotesRepository {
    private val notes: List<Note> = listOf(
        Note(
            "Первая заметка",
            "Текст первой заметки",
            0xfff06292.toInt()
        ),
        Note(
            "Вторая заметка",
            "Текст второй заметки",
            0xff9575cd.toInt()
        ),
        Note(
            "Третья заметка",
            "Текст третьей заметки",
            0xff64b5f6.toInt()
        ),
        Note(
            "Четвертая заметка",
            "Текст четвертой заметки",
            0xfff4db6ac.toInt()
        ),
        Note(
            "Пятая заметка",
            "Текст пятой заметки",
            0xffb2ff59.toInt()
        ),
        Note(
            "Шестая заметка",
            "Текст шестой заметки",
            0xfffeb3b.toInt()
        )
    )
//    fun getNotes() : List<Note> {
//        return notes
//    }
fun getNotes(): List<Note> {
    return notes
}
}