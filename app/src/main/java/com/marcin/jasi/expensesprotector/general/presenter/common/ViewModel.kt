package com.marcin.jasi.expensesprotector.general.presenter.common

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable

abstract class ViewModel<VS : ViewState, I : BaseIntent> : ViewModel() {

    abstract fun processIntents(intents: Observable<I>)

    abstract fun states(): Observable<VS>
}