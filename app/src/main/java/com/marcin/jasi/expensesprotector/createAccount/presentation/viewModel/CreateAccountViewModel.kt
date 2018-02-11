package com.marcin.jasi.expensesprotector.createAccount.presentation.viewModel

import com.marcin.jasi.expensesprotector.createAccount.presentation.ui.CreateAccountIntent
import com.marcin.jasi.expensesprotector.createAccount.presentation.ui.CreateAccountViewState
import com.marcin.jasi.expensesprotector.general.presenter.common.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor() : ViewModel<CreateAccountViewState, CreateAccountIntent>() {

    private val intentSubject: PublishSubject<CreateAccountIntent> = PublishSubject.create()
    private val oneInitialIntentTransformer = ObservableTransformer<CreateAccountIntent, CreateAccountIntent> {
        it.publish {
            Observable.merge(
                    it.ofType(CreateAccountIntent.InitialIntent::class.java).take(1),
                    it.filter { it != CreateAccountIntent.InitialIntent::class.java }
            )
        }
    }
    private val reducer: BiFunction<CreateAccountViewState, CreateAccountIntent, CreateAccountViewState> =
            BiFunction { _, result ->
                when (result) {
                    is CreateAccountIntent.CreateIntent -> CreateAccountViewState.Creating
                    CreateAccountIntent.BackIntent -> CreateAccountViewState.Cancel
                    CreateAccountIntent.InitialIntent -> CreateAccountViewState.Idle
                }
            }

    override fun processIntents(intents: Observable<CreateAccountIntent>) {
        intents.subscribe(intentSubject)
    }

    override fun states(): Observable<CreateAccountViewState> {
        return intentSubject
                .compose(oneInitialIntentTransformer)
                .scan(CreateAccountViewState.Idle, reducer)
    }

}