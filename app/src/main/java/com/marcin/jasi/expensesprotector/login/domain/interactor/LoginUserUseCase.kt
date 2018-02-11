package com.marcin.jasi.expensesprotector.login.domain.interactor

import com.marcin.jasi.expensesprotector.general.domain.entity.User
import com.marcin.jasi.expensesprotector.general.domain.helpers.PostExecutionThread
import com.marcin.jasi.expensesprotector.general.domain.helpers.QueryUseCase
import com.marcin.jasi.expensesprotector.general.domain.helpers.ThreadExecutor
import com.marcin.jasi.expensesprotector.login.domain.interactor.LoginUserUseCase.LoginStatus
import com.marcin.jasi.expensesprotector.login.domain.repository.LoginActivityRepository
import io.reactivex.Observable

class LoginUserUseCase(
        private val repository: LoginActivityRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : QueryUseCase<LoginStatus, User>(threadExecutor, postExecutionThread) {

    sealed class LoginStatus {
        object BadPassword : LoginStatus()
        object UserDontExist : LoginStatus()
        object LoggedIn : LoginStatus()
    }

    private val emptyUser: User = User("", "")

    override fun buildUseCaseObservable(params: User): Observable<LoginStatus> {
        return Observable.just(params.login)
                .map { login -> repository.getUserByLogin(login) ?: emptyUser }
                .map { user -> checkUserFromDB(user, params) }
    }

    private fun checkUserFromDB(user: User, params: User): LoginStatus {
        if (emptyUser == user)
            return LoginStatus.UserDontExist

        return if (user.password == params.password && user.login == params.login)
            LoginStatus.LoggedIn
        else
            LoginStatus.BadPassword
    }

}