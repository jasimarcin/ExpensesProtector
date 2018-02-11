package com.marcin.jasi.expensesprotector.login.data.repository

import com.marcin.jasi.expensesprotector.general.domain.entity.User
import com.marcin.jasi.expensesprotector.login.data.repository.datasources.LoginActivityMemoryDataSource
import com.marcin.jasi.expensesprotector.login.domain.repository.LoginActivityRepository
import io.reactivex.Completable
import io.reactivex.Single

class LoginActivityRepositoryImpl(private val memoryDataSource: LoginActivityMemoryDataSource) : LoginActivityRepository {

    override fun addUser(user: User): Completable = memoryDataSource.addUser(user)

    override fun getUserByLogin(login: String): User? = memoryDataSource.getUserByLogin(login)

}