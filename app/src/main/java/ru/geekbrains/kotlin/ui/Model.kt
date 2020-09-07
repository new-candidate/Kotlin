package ru.geekbrains.kotlin.ui

import androidx.lifecycle.MutableLiveData

class Model  {

    val myCount  : MutableLiveData<Int> = MutableLiveData()
    var counter = 0

    init {
        myCount.value = counter
    }

    fun click  () {
        counter += 1
        myCount.value = counter
    }
}