package ru.geekbrains.kotlin.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import ru.geekbrains.kotlin.R
import ru.geekbrains.kotlin.data.entity.Note

inline fun Note.Color.getColorInt(context: Context) = ContextCompat.getColor(context, getColorRes(context))

inline fun Note.Color.getColorRes(context: Context) = when (this) {
    Note.Color.WHITE -> R.color.color_white
    Note.Color.VIOLET -> R.color.color_violet
    Note.Color.YELLOW -> R.color.color_yellow
    Note.Color.RED -> R.color.color_red
    Note.Color.PINK -> R.color.color_pink
    Note.Color.GREEN -> R.color.color_green
    else -> R.color.color_white
}