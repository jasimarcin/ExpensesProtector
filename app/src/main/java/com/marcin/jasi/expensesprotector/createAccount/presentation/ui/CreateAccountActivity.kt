package com.marcin.jasi.expensesprotector.createAccount.presentation.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.marcin.jasi.expensesprotector.R
import com.marcin.jasi.expensesprotector.createAccount.presentation.viewModel.CreateAccountViewModel
import com.marcin.jasi.expensesprotector.di.ViewModelFactory
import com.marcin.jasi.expensesprotector.di.annotation.ActivityScope
import com.marcin.jasi.expensesprotector.general.presenter.common.CommonActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


@ActivityScope
class CreateAccountActivity : CommonActivity<CreateAccountViewState, CreateAccountIntent, CreateAccountViewModel>() {

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, CreateAccountActivity::class.java)
    }

    @BindView(R.id.create_progress)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.cancel_button)
    lateinit var cancelButton: Button
    @BindView(R.id.create_button)
    lateinit var createButton: Button

    @BindView(R.id.login_input)
    lateinit var loginInput: EditText
    @BindView(R.id.password_input)
    lateinit var passwordInput: EditText
    @BindView(R.id.confirm_password_input)
    lateinit var confirmPasswordInput: EditText

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val intentPublisher: BehaviorSubject<CreateAccountIntent> = BehaviorSubject.create()
    private val disposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateAccountViewModel::class.java)

        disposable.add(viewModel.states().subscribe { render(it) })
        viewModel.processIntents(intents())

        initViews()
    }

    private fun initViews() {
        cancelButton.setOnClickListener({ intentPublisher.onNext(CreateAccountIntent.BackIntent) })
        createButton.setOnClickListener({
            intentPublisher.onNext(CreateAccountIntent.CreateIntent(
                    loginInput.text.toString(),
                    passwordInput.text.toString(),
                    confirmPasswordInput.text.toString()))
        })
    }

    override fun render(viewState: CreateAccountViewState) {
        if (viewState.back || viewState.created) {
            finish()
            return
        }

        progressBar.apply {
            visibility = if (viewState.progressBar) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun intents(): Observable<CreateAccountIntent> {
        return Observable.merge(intentPublisher, initialIntent())
    }

    private fun initialIntent(): Observable<CreateAccountIntent> = Observable.just(CreateAccountIntent.InitialIntent)

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
