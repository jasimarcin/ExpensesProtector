package com.marcin.jasi.expensesprotector.di.module

import com.marcin.jasi.expensesprotector.createAccount.injection.module.CreateAccountModule
import com.marcin.jasi.expensesprotector.createAccount.presentation.ui.CreateAccountActivity
import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.login.injection.module.LoginActivityModule
import com.marcin.jasi.expensesprotector.login.presentation.ui.LoginActivity
import com.marcin.jasi.expensesprotector.splashScreen.presentation.injection.module.SplashScreenActivityModule
import com.marcin.jasi.expensesprotector.splashScreen.presentation.ui.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBindsModule {

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    @ActivityScope
    abstract fun provideLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SplashScreenActivityModule::class])
    @ActivityScope
    abstract fun provideSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector(modules = [CreateAccountModule::class])
    @ActivityScope
    abstract fun provideCreateAccountActivity(): CreateAccountActivity

}