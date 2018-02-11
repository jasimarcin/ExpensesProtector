package com.marcin.jasi.expensesprotector.login.presentation.ui

import com.marcin.jasi.expensesprotector.general.presenter.common.ViewState


sealed class LoginActivityViewState(
        val isLoading: Boolean = false,
        val showError: Boolean = false,
        val finishActivity: Boolean = false,
        val loggedIn: Boolean = false,
        val createAccount: Boolean = false
) : ViewState {

    object Idle : LoginActivityViewState()
    object Loading : LoginActivityViewState(isLoading = true)
    object Error : LoginActivityViewState(showError = true)
    object LoggedIn : LoginActivityViewState(loggedIn = true)
    object Finish : LoginActivityViewState(finishActivity = true)
    object CreateAccount : LoginActivityViewState(createAccount = true)

}