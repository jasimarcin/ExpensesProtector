package com.marcin.jasi.expensesprotector.login.presentation.viewModel

import android.util.Log
import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.general.domain.entity.User
import com.marcin.jasi.expensesprotector.general.domain.helpers.PostExecutionThread
import com.marcin.jasi.expensesprotector.general.domain.helpers.ThreadExecutor
import com.marcin.jasi.expensesprotector.general.presenter.common.ViewModel
import com.marcin.jasi.expensesprotector.login.domain.interactor.LoginUserUseCase
import com.marcin.jasi.expensesprotector.login.presentation.ui.LoginActivityIntent
import com.marcin.jasi.expensesprotector.login.presentation.ui.LoginActivityViewState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(
        private val loginUserUseCase: LoginUserUseCase,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread)
    : ViewModel<LoginActivityViewState, LoginActivityIntent>() {


    private val intentSubject: PublishSubject<LoginActivityIntent> = PublishSubject.create()
    private val isLoading: AtomicBoolean = AtomicBoolean(false)

    private val intentTransformer: ObservableTransformer<LoginActivityIntent, LoginActivityIntent> =
            ObservableTransformer {
                it.publish {
                    Observable.merge(
                            it.ofType(LoginActivityIntent.InitialIntent::class.java).take(1),
                            it.filter({ intent -> intent != LoginActivityIntent.InitialIntent })
                    )
                }
            }

    private val intentInterpreter: ObservableTransformer<LoginActivityIntent, LoginActivityPartialViewState> =
            ObservableTransformer {
                it.publish {
                    Observable.merge(loginIntentInterpreter(it), intentInterpreter(it))
                }
            }

    override fun processIntents(intents: Observable<LoginActivityIntent>) {
        intents.subscribe(intentSubject)
    }

    override fun states(): Observable<LoginActivityViewState> {
        return intentSubject
                .observeOn(Schedulers.from(threadExecutor))
                .subscribeOn(Schedulers.from(threadExecutor))
                .compose(intentTransformer) // filter initial intent
                .compose(intentInterpreter)
                .scan(LoginActivityViewState.Idle, reducer)
                .observeOn(postExecutionThread.scheduler)
                .doOnNext { Log.d("LoginViewSate", "$it") }
//                .skip(1)
    }

    private val reducer: BiFunction<LoginActivityViewState, LoginActivityPartialViewState, LoginActivityViewState> =
            BiFunction { previousState, result ->
                when (result) {
                    LoginActivityPartialViewState.LogInError -> LoginActivityViewState.Error
                    LoginActivityPartialViewState.LogedIn -> LoginActivityViewState.LoggedIn
                    LoginActivityPartialViewState.Cancel -> LoginActivityViewState.Finish
                    LoginActivityPartialViewState.CreateAccount -> LoginActivityViewState.CreateAccount
                    LoginActivityPartialViewState.Idle -> LoginActivityViewState.Idle
                    LoginActivityPartialViewState.Loading -> LoginActivityViewState.Loading
                }
            }

    private fun loginIntentInterpreter(chain: Observable<LoginActivityIntent>): Observable<LoginActivityPartialViewState> {
        return chain
                .filter { it is LoginActivityIntent.LoginIntent }
                .filter { !isLoading.get() }
                .flatMap { user ->

                    if (user !is LoginActivityIntent.LoginIntent)
                        return@flatMap Observable.just(LoginActivityPartialViewState.LogInError)

                    return@flatMap loginUserUseCase
                            .getObservable(User(user.login, user.password))
                            .doOnSubscribe { isLoading.set(true) }
                            .doOnNext { isLoading.set(false) }
                            .map { loginStatus ->
                                when (loginStatus) {
                                    LoginUserUseCase.LoginStatus.BadPassword -> LoginActivityPartialViewState.LogInError
                                    LoginUserUseCase.LoginStatus.LoggedIn -> LoginActivityPartialViewState.LogedIn
                                    LoginUserUseCase.LoginStatus.UserDontExist -> LoginActivityPartialViewState.LogInError
                                }
                            }
                            .publish {
                                it.mergeWith(Observable.just(LoginActivityPartialViewState.Loading))
                            }
                }

    }

    private fun intentInterpreter(chain: Observable<LoginActivityIntent>): Observable<LoginActivityPartialViewState> {
        return chain
                .filter { it !is LoginActivityIntent.LoginIntent }
                .map { data ->
                    when (data) {
                        LoginActivityIntent.CreateAccout -> LoginActivityPartialViewState.CreateAccount
                        LoginActivityIntent.CancelIntent -> LoginActivityPartialViewState.Cancel
                        else -> LoginActivityPartialViewState.Idle
                    }
                }
    }

}