package ru.geekbrains.kotlin.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    var id: String,
    var title: String,
    var text: String,
    var color: Color,
    var lastChanged: Date = Date()
) :
    Parcelable {
    enum class Color {
        WHITE, YELLOW, GREEN, BLUE, RED, VIOLET, PINK
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Note
        if (id != other.id) return false
        return true
    }
}