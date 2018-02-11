package com.marcin.jasi.expensesprotector.login.domain.entity.mapper

import com.marcin.jasi.expensesprotector.database.entities.UserTable
import com.marcin.jasi.expensesprotector.general.data.common.DataMapper
import com.marcin.jasi.expensesprotector.general.domain.entity.User


class UserMapper : DataMapper<UserTable, User>{

    override fun transform(from: UserTable): User {
        return User(from.login,from.password)
    }

}