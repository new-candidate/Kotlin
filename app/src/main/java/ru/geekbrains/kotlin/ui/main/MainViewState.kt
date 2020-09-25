package ru.geekbrains.kotlin.ui.main

import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)
//class MainViewState(val notes: List<Note> = emptyList(), error: Throwable? = null) : BaseViewState<List<Note>>(notes, error)