package com.marcin.jasi.expensesprotector.createAccount.presentation.ui

import com.marcin.jasi.expensesprotector.general.presenter.common.BaseIntent

sealed class CreateAccountIntent : BaseIntent {

    object InitialIntent : CreateAccountIntent()
    object BackIntent: CreateAccountIntent()
    data class CreateIntent(var login:String,val password:String,val confirmPassword:String): CreateAccountIntent()

}