package com.marcin.jasi.expensesprotector.di.module

import android.arch.persistence.room.Room
import com.marcin.jasi.expensesprotector.Application
import com.marcin.jasi.expensesprotector.database.AppDatabase
import com.marcin.jasi.expensesprotector.general.data.DataConstants
import com.marcin.jasi.expensesprotector.general.domain.helpers.PostExecutionThread
import com.marcin.jasi.expensesprotector.general.domain.helpers.ThreadExecutor
import com.marcin.jasi.expensesprotector.general.domain.helpers.UiThread
import com.marcin.jasi.expensesprotector.general.domain.helpers.WorkingExecutor
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule {

    @Provides
    fun provideDatabaseModule(application: Application): AppDatabase =
            Room.databaseBuilder(application, AppDatabase::class.java, DataConstants.DATABASE_NAME).build()

    @Provides
    fun provideThreadExecutor(): ThreadExecutor = WorkingExecutor()

    @Provides
    fun providePostExecutionThread(): PostExecutionThread = UiThread()

}