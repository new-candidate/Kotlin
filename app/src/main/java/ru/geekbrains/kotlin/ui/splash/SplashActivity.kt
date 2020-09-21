package ru.geekbrains.kotlin.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.kotlin.ui.base.BaseActivity
import ru.geekbrains.kotlin.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>(){

    override val viewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
//        viewModel.requestUser()
        Handler().postDelayed({viewModel.requestUser()}, 4000)
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