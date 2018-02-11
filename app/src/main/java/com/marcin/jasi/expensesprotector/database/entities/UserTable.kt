package com.marcin.jasi.expensesprotector.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import com.marcin.jasi.expensesprotector.database.entities.UserTable.Companion.ID_KEY
import com.marcin.jasi.expensesprotector.database.entities.UserTable.Companion.LOGIN_KEY
import com.marcin.jasi.expensesprotector.database.entities.UserTable.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME,
        primaryKeys = [ID_KEY],
        indices = [Index(value = LOGIN_KEY, unique = true)]
)
data class UserTable(
        @ColumnInfo(name = ID_KEY) val id: Long,
        @ColumnInfo(name = LOGIN_KEY) val login: String,
        @ColumnInfo(name = PASSWORD_KEY) val password: String
) {

    companion object {
        const val LOGIN_KEY: String = "login"
        const val ID_KEY: String = "id"
        const val TABLE_NAME: String = "User"
        const val PASSWORD_KEY: String = "password"
    }

}

