package com.marcin.jasi.expensesprotector.login.data.repository.datasources

import com.marcin.jasi.expensesprotector.database.dao.UserDao
import com.marcin.jasi.expensesprotector.database.entities.UserTable
import com.marcin.jasi.expensesprotector.general.data.common.DataMapper
import com.marcin.jasi.expensesprotector.general.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Single

class LoginActivityMemoryDataSource(
        private val userDao: UserDao,
        private val userTableMapper: DataMapper<User, UserTable>,
        private val userMapper: DataMapper<UserTable, User>) {

    fun addUser(user: User): Completable {
        return Completable.fromCallable { userDao.insert(userTableMapper.transform(user)) }
    }

    fun getUserByLogin(login: String): User? {

        val user: UserTable? = userDao.getUserByLogin(login)

        return if (user != null)
            userMapper.transform(user)
        else
            user
    }
}