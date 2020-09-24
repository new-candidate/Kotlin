package ru.geekbrains.kotlin.extensions

import android.content.Context

inline fun Context.dip(value: Int) = resources.displayMetrics.density * value