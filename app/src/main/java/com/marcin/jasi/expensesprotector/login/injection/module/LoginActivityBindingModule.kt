package com.marcin.jasi.expensesprotector.login.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.marcin.jasi.expensesprotector.di.ViewModelFactory
import com.marcin.jasi.expensesprotector.di.annotation.ViewModelKey
import com.marcin.jasi.expensesprotector.login.presentation.viewModel.LoginActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [LoginActivityModule::class])
abstract class LoginActivityBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

//    @Binds
//    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}