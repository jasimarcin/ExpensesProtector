package com.marcin.jasi.expensesprotector.di.module

import android.arch.lifecycle.ViewModelProvider
import com.marcin.jasi.expensesprotector.createAccount.injection.module.CreateAccountBindingModule
import com.marcin.jasi.expensesprotector.createAccount.presentation.ui.CreateAccountActivity
import com.marcin.jasi.expensesprotector.di.ViewModelFactory
import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.login.injection.module.LoginActivityBindingModule
import com.marcin.jasi.expensesprotector.login.presentation.ui.LoginActivity
import com.marcin.jasi.expensesprotector.splashScreen.presentation.injection.module.SplashScreenActivityModule
import com.marcin.jasi.expensesprotector.splashScreen.presentation.ui.SplashScreenActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBindsModule {

    @ContributesAndroidInjector(modules = [LoginActivityBindingModule::class])
    @ActivityScope
    abstract fun provideLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SplashScreenActivityModule::class])
    @ActivityScope
    abstract fun provideSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector(modules = [CreateAccountBindingModule::class])
    @ActivityScope
    abstract fun provideCreateAccountActivity(): CreateAccountActivity

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}