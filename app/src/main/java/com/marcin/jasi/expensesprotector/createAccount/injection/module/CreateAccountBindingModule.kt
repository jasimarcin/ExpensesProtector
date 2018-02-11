package com.marcin.jasi.expensesprotector.createAccount.injection.module

import android.arch.lifecycle.ViewModel
import com.marcin.jasi.expensesprotector.createAccount.presentation.viewModel.CreateAccountViewModel
import com.marcin.jasi.expensesprotector.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreateAccountBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateAccountViewModel::class)
    abstract fun bindCreateAccountViewModel(viewModel: CreateAccountViewModel): ViewModel

}