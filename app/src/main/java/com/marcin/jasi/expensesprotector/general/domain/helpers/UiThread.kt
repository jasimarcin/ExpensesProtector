package com.marcin.jasi.expensesprotector.general.domain.helpers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.Scheduler


class UiThread : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}