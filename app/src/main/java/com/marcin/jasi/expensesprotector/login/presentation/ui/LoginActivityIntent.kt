package com.marcin.jasi.expensesprotector.login.presentation.ui

import com.marcin.jasi.expensesprotector.general.presenter.common.BaseIntent

sealed class LoginActivityIntent : BaseIntent {

    object InitialIntent : LoginActivityIntent()
    object CancelIntent : LoginActivityIntent()
    object CreateAccout : LoginActivityIntent()
    data class LoginIntent(val login: String, val password: String) : LoginActivityIntent()

}