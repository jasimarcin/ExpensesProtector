package com.marcin.jasi.expensesprotector.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.marcin.jasi.expensesprotector.createAccount.presentation.viewModel.CreateAccountViewModel
import com.marcin.jasi.expensesprotector.di.ViewModelFactory
import com.marcin.jasi.expensesprotector.di.annotation.ViewModelKey
import com.marcin.jasi.expensesprotector.login.presentation.viewModel.LoginActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ApplicationBindsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateAccountViewModel::class)
    abstract fun bindCreateAccountViewModel(viewModel: CreateAccountViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}