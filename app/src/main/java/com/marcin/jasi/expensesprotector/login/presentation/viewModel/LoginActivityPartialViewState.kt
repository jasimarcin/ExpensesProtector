package com.marcin.jasi.expensesprotector.login.presentation.viewModel

sealed class LoginActivityPartialViewState {

    object Loading : LoginActivityPartialViewState()
    object LogedIn : LoginActivityPartialViewState()
    object CreateAccount : LoginActivityPartialViewState()
    object Cancel : LoginActivityPartialViewState()
    object Idle : LoginActivityPartialViewState()
    object LogInError : LoginActivityPartialViewState()

}