package com.marcin.jasi.expensesprotector.login.domain.entity.mapper

import com.marcin.jasi.expensesprotector.database.entities.UserTable
import com.marcin.jasi.expensesprotector.general.data.common.DataMapper
import com.marcin.jasi.expensesprotector.general.domain.entity.User

class UserTableMapper : DataMapper<User, UserTable> {

    override fun transform(from: User): UserTable {
        return UserTable(0, from.login, from.password)
    }

}