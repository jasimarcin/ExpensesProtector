package com.marcin.jasi.expensesprotector.splashScreen.presentation.injection.module

import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.login.presentation.helper.Navigator
import com.marcin.jasi.expensesprotector.splashScreen.presentation.ui.SplashScreenActivity
import dagger.Module
import dagger.Provides

@Module
class SplashScreenActivityModule {

    @Provides
    @ActivityScope
    fun provideNavigator(activity: SplashScreenActivity): Navigator = Navigator(activity)
}