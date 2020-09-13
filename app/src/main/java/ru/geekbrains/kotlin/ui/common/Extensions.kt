package ru.geekbrains.kotlin.ui.common

import ru.geekbrains.kotlin.R
import ru.geekbrains.kotlin.data.entity.Note

fun Note.Color.getResources(): Int {
    val color = when (this) {
        Note.Color.WHITE -> R.color.color_white
        Note.Color.YELLOW -> R.color.color_yellow
        Note.Color.GREEN -> R.color.color_green
        Note.Color.BLUE -> R.color.color_blue
        Note.Color.RED -> R.color.color_red
        Note.Color.VIOLET -> R.color.color_violet
        Note.Color.PINK -> R.color.color_pink
    }
    return color
}
