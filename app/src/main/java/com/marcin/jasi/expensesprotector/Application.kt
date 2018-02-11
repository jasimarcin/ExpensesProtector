package com.marcin.jasi.expensesprotector

import android.app.Activity
import android.app.Application
import com.marcin.jasi.expensesprotector.di.component.DaggerApplicationComponent
import dagger.android.*
import javax.inject.Inject

class Application : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        initDependencies()
    }

    private fun initDependencies() {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }
}