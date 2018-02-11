package com.marcin.jasi.expensesprotector.login.domain.repository

import com.marcin.jasi.expensesprotector.general.domain.entity.User
import io.reactivex.Completable


interface LoginActivityRepository {

    fun addUser(user: User): Completable

    fun getUserByLogin(login: String): User?

}