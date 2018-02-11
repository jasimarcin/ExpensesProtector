package com.marcin.jasi.expensesprotector.login.presentation.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.marcin.jasi.expensesprotector.R
import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.general.presenter.common.CommonActivity
import com.marcin.jasi.expensesprotector.login.presentation.helper.Navigator
import com.marcin.jasi.expensesprotector.login.presentation.viewModel.LoginActivityViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@ActivityScope
class LoginActivity : CommonActivity<LoginActivityViewState, LoginActivityIntent, LoginActivityViewModel>() {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    @BindView(R.id.login_progress)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.cancel_button)
    lateinit var cancelButton: Button
    @BindView(R.id.login_button)
    lateinit var loginButton: Button
    @BindView(R.id.login_input)
    lateinit var loginInput: EditText
    @BindView(R.id.password_input)
    lateinit var passwordInput: EditText
    @BindView(R.id.create_account)
    lateinit var createAccount: FloatingActionButton

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val intentPublisher: BehaviorSubject<LoginActivityIntent> = BehaviorSubject.create()
    private val disposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ButterKnife.bind(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginActivityViewModel::class.java)

        connectViewModel()
        initView()
    }

    private fun initView() {
        loginButton.setOnClickListener {
            intentPublisher.onNext(LoginActivityIntent.LoginIntent(loginInput.text.toString(), passwordInput.text.toString()))
        }

        cancelButton.setOnClickListener { intentPublisher.onNext(LoginActivityIntent.CancelIntent) }
        createAccount.setOnClickListener({ intentPublisher.onNext(LoginActivityIntent.CreateAccout) })
    }

    private fun connectViewModel() {
        disposable.add(viewModel.states()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ render(it) }))
        viewModel.processIntents(intents())
    }

    override fun render(viewState: LoginActivityViewState) {
        Log.d("LoginActivityRender", "$viewState")

        if (viewState.finishActivity) {
            finish()
            return
        }

        progressBar.apply {
            visibility = if (viewState.isLoading) View.VISIBLE else View.INVISIBLE
        }

        loginButton.apply {
            isClickable = !viewState.isLoading
            isPressed = viewState.isLoading
        }

        loginInput.apply {
            isEnabled = !viewState.isLoading
        }

        passwordInput.apply {
            isEnabled = !viewState.isLoading
        }

        if (viewState.createAccount)
            navigator.startCreateAcountActivity()

        if (viewState.loggedIn)
            Toast.makeText(this, "LOGGED IN", Toast.LENGTH_LONG).show()

        if (viewState.showError)
            Toast.makeText(this, "LOGIN ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun intents(): Observable<LoginActivityIntent> {
        return Observable.merge(initialIntent(), intentPublisher)
    }

    private fun initialIntent(): Observable<LoginActivityIntent> {
        return Observable.just(LoginActivityIntent.InitialIntent)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}

