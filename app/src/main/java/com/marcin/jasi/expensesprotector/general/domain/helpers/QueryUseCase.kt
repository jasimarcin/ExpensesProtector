package com.marcin.jasi.expensesprotector.general.domain.helpers

import dagger.internal.Preconditions
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.internal.observers.LambdaObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.functions.ObjectHelper


abstract class QueryUseCase<T, Params>(private val threadExecutor: ThreadExecutor, private val postExecutionThread: PostExecutionThread) {


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()



    protected abstract fun buildUseCaseObservable(params: Params): Observable<T>

    fun getObservable(params: Params): Observable<T> {
        return buildUseCaseObservable(params)
    }

    fun execute(observer: DisposableObserver<T>, params: Params) {
        Preconditions.checkNotNull(observer)

        val observable = buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler, true)

        addDisposable(observable.subscribeWith(observer))
    }

    fun execute(observer: LambdaObserver<T>, params: Params) {
        Preconditions.checkNotNull(observer)
        observe(observer, params)
    }

    fun execute(onNext: Consumer<T>, onError: Consumer<in Throwable>, onComplete: Action,
                onSubscribe: Consumer<in Disposable>, params: Params) {
        ObjectHelper.requireNonNull(onNext, "onNext is null")
        ObjectHelper.requireNonNull(onError, "onError is null")
        ObjectHelper.requireNonNull(onComplete, "onComplete is null")
        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null")

        val lambdaObserver = LambdaObserver<T>(onNext, onError, onComplete, onSubscribe)
        observe(lambdaObserver, params)
    }

    fun execute(onNext: Consumer<T>, params: Params) {
        execute(onNext, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.emptyConsumer(), params)
    }

    fun execute(onNext: Consumer<T>, onComplete: Action, params: Params) {
        execute(onNext, Functions.emptyConsumer(), onComplete, Functions.emptyConsumer(), params)
    }

    fun execute(onNext: Consumer<T>, onError: Consumer<in Throwable>, params: Params) {
        execute(onNext, onError, Functions.EMPTY_ACTION, Functions.emptyConsumer(), params)
    }

    fun execute(onNext: Consumer<T>, onError: Consumer<in Throwable>, onComplete: Action, params: Params) {
        execute(onNext, onError, onComplete, Functions.emptyConsumer(), params)
    }

    private fun observe(observer: LambdaObserver<T>, params: Params) {
        val observable = buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler, true)

        observable.subscribe(observer)
        addDisposable(observer)
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun addDisposable(disposable: Disposable) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(compositeDisposable)
        compositeDisposable.add(disposable)
    }
}