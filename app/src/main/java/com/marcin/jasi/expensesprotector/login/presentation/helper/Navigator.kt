package com.marcin.jasi.expensesprotector.login.presentation.helper

import android.content.Context
import com.marcin.jasi.expensesprotector.createAccount.presentation.ui.CreateAccountActivity
import com.marcin.jasi.expensesprotector.login.presentation.ui.LoginActivity

class Navigator(private val context: Context) {

    fun startLoginActivity() {
        context.startActivity(LoginActivity.getStartIntent(context))
    }

    fun startCreateAcountActivity() {
        context.startActivity(CreateAccountActivity.getStartIntent(context))
    }

}