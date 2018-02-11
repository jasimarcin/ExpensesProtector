package com.marcin.jasi.expensesprotector.general.presenter.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

open abstract class CommonActivity<VS : ViewState, I : BaseIntent, VM : ViewModel<VS, I>> : AppCompatActivity(), View<VS, I> {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}