package com.marcin.jasi.expensesprotector.splashScreen.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marcin.jasi.expensesprotector.R
import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.login.presentation.helper.Navigator
import dagger.android.AndroidInjection
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
clasSplashScreenActivity : AppCompatActivity() {

    companion object {
        const val DELAY_TIME: Long = 2L
        val DELAY_TIME_UNIT: TimeUnit = TimeUnit.SECONDS
    }

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()
        countToStartLoginActivity()
    }

    private fun countToStartLoginActivity() {
        Observable.timer(DELAY_TIME, DELAY_TIME_UNIT)
                .doOnNext {
                    navigator.startLoginActivity()
                    finish()
                }
                .subscribe()
    }
}
