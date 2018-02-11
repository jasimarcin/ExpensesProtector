package com.marcin.jasi.expensesprotector.database.dao

import android.arch.persistence.room.*
import com.marcin.jasi.expensesprotector.database.entities.UserTable


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(user: UserTable?)

    @Query("SELECT * FROM ${UserTable.TABLE_NAME}")
    fun getAll(): List<UserTable?>

    @Delete
    fun delete(user: UserTable?)

    @Query("SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.LOGIN_KEY} LIKE :login")
    fun getUserByLogin(login: String): UserTable?
}