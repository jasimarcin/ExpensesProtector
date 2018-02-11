package com.marcin.jasi.expensesprotector.general.domain.helpers

import android.support.annotation.NonNull
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class WorkingExecutor : ThreadExecutor {

    companion object {
        const val MAXIMUM_POOL_SIZE: Int = 5
        const val THREAD_ALIVE_TIME: Long = 10L
        const val CORE_POOLS_SIZE: Int = 3
    }

    private val threadPoolExecutor: ThreadPoolExecutor

    // threads for bg jobs
    init {
        threadPoolExecutor = ThreadPoolExecutor(
                CORE_POOLS_SIZE, MAXIMUM_POOL_SIZE,
                THREAD_ALIVE_TIME, TimeUnit.SECONDS,
                LinkedBlockingQueue(), JobThreadFactory())
    }

    override fun execute(@NonNull runnable: Runnable) {
        this.threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(@NonNull runnable: Runnable): Thread {
            return Thread(runnable, "android_" + counter++)
        }
    }


}