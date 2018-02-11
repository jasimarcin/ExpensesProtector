package com.marcin.jasi.expensesprotector.general.domain.helpers

import io.reactivex.Scheduler


interface PostExecutionThread {
    val scheduler: Scheduler
}