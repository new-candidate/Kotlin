package ru.geekbrains.kotlin.ui.splash

import android.os.Handler
import ru.geekbrains.kotlin.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrains.kotlin.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>(){

    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({viewModel.requestUser()}, 1000)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

}