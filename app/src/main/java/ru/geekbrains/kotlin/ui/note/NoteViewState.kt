package ru.geekbrains.kotlin.ui.note

import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)