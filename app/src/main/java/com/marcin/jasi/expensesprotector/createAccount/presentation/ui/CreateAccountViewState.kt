package com.marcin.jasi.expensesprotector.createAccount.presentation.ui

import com.marcin.jasi.expensesprotector.general.presenter.common.ViewState

sealed class CreateAccountViewState(
        val progressBar: Boolean = false,
        val error: Boolean = false,
        val back: Boolean = false,
        val created: Boolean = false
) : ViewState {

    object Idle : CreateAccountViewState()
    object Error : CreateAccountViewState(error = true)
    object Created : CreateAccountViewState(created = true)
    object Creating : CreateAccountViewState(progressBar = true)
    object Cancel : CreateAccountViewState(back = true)

}