package com.marcin.jasi.expensesprotector.login.injection.module

import com.marcin.jasi.expensesprotector.database.AppDatabase
import com.marcin.jasi.expensesprotector.database.entities.UserTable
import com.marcin.jasi.expensesprotector.general.data.common.DataMapper
import com.marcin.jasi.expensesprotector.general.domain.entity.User
import com.marcin.jasi.expensesprotector.general.domain.helpers.PostExecutionThread
import com.marcin.jasi.expensesprotector.general.domain.helpers.ThreadExecutor
import com.marcin.jasi.expensesprotector.login.data.repository.LoginActivityRepositoryImpl
import com.marcin.jasi.expensesprotector.login.data.repository.datasources.LoginActivityMemoryDataSource
import com.marcin.jasi.expensesprotector.login.domain.entity.mapper.UserMapper
import com.marcin.jasi.expensesprotector.login.domain.entity.mapper.UserTableMapper
import com.marcin.jasi.expensesprotector.login.domain.interactor.LoginUserUseCase
import com.marcin.jasi.expensesprotector.login.domain.repository.LoginActivityRepository
import com.marcin.jasi.expensesprotector.login.presentation.helper.Navigator
import com.marcin.jasi.expensesprotector.login.presentation.ui.LoginActivity
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule {

    @Provides
    fun provideNavigator(activity: LoginActivity): Navigator = Navigator(activity)

    @Provides
    fun provideUserTableMapper(): DataMapper<User, UserTable> = UserTableMapper()

    @Provides
    fun provideUserMapper(): DataMapper<UserTable, User> = UserMapper()

    @Provides
    fun provideMemoryDataSource(database: AppDatabase, userMapper: DataMapper<UserTable, User>, userTableMapper: DataMapper<User, UserTable>)
            : LoginActivityMemoryDataSource = LoginActivityMemoryDataSource(database.userDao(), userTableMapper, userMapper)

    @Provides
    fun provideLoginRepository(memoryDataSource: LoginActivityMemoryDataSource)
            : LoginActivityRepository = LoginActivityRepositoryImpl(memoryDataSource)

    @Provides
    fun provideLoginUseCase(repository: LoginActivityRepository,
                            threadExecutor: ThreadExecutor,
                            postExecutionThread: PostExecutionThread)
            : LoginUserUseCase = LoginUserUseCase(repository, threadExecutor, postExecutionThread)
}