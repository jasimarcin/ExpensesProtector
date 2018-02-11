package com.marcin.jasi.expensesprotector.general.domain.helpers

import dagger.internal.Preconditions
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Completable
import io.reactivex.internal.observers.CallbackCompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.functions.ObjectHelper


abstract class CommandUseCase<Params>(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()



    protected abstract fun buildUseCaseObservable(params: Params): Completable

    fun getCompletable(params: Params): Completable {
        return buildUseCaseObservable(params)
    }

    fun execute(observer: CallbackCompletableObserver, params: Params) {
        Preconditions.checkNotNull(observer)
        observe(observer, params)
    }

    fun execute(onComplete: Action, onError: Consumer<in Throwable>, params: Params) {
        ObjectHelper.requireNonNull(onError, "onError is null")
        ObjectHelper.requireNonNull(onComplete, "onComplete is null")

        val observer = CallbackCompletableObserver(onError, onComplete)
        observe(observer, params)
    }

    fun execute(onComplete: Action, params: Params) {
        execute(onComplete, Functions.emptyConsumer(), params)
    }

    fun execute(errorConsumer: Consumer<in Throwable>, params: Params) {
        execute(Functions.EMPTY_ACTION, errorConsumer, params)
    }

    fun execute(params: Params) {
        execute(Functions.EMPTY_ACTION, Functions.emptyConsumer(), params)
    }

    private fun observe(observer: CallbackCompletableObserver, params: Params) {
        val observable = buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)

        observable.subscribe(observer)
        addDisposable(observer)
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(compositeDisposable)
        compositeDisposable.add(disposable)
    }

}