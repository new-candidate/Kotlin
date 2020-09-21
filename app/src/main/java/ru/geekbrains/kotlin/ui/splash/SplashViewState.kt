package ru.geekbrains.kotlin.ui.splash

import ru.geekbrains.kotlin.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null): BaseViewState<Boolean?>(authenticated, error)