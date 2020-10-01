package ru.geekbrains.kotlin.extensions

import android.view.View

inline fun View.dip(value: Int) = context.dip(value)
