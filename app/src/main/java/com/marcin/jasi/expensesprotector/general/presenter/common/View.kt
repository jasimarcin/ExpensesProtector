package com.marcin.jasi.expensesprotector.general.presenter.common

import io.reactivex.Observable

interface View<VS : ViewState, I : BaseIntent> {
    fun render(viewState: VS)

    fun intents(): Observable<I>
}
