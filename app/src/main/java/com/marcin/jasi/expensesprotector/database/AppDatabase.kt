package com.marcin.jasi.expensesprotector.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.marcin.jasi.expensesprotector.database.dao.UserDao
import com.marcin.jasi.expensesprotector.database.entities.UserTable
import com.marcin.jasi.expensesprotector.general.data.DataConstants


@Database(version = DataConstants.DATABASE_VERSION, entities = [UserTable::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}