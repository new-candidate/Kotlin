package ru.geekbrains.kotlin.ui.note

import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}