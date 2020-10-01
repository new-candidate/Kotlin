package ru.geekbrains.kotlin.ui.main

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.geekbrains.kotlin.di.appModule
import ru.geekbrains.kotlin.di.mainModule
import ru.geekbrains.kotlin.di.noteModule
import ru.geekbrains.kotlin.di.splashModule

class App: Application() {

    companion object {
        lateinit var instance : App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}