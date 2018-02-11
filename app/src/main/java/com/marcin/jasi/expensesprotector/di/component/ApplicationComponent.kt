package com.marcin.jasi.expensesprotector.di.component

import com.marcin.jasi.expensesprotector.Application
import com.marcin.jasi.expensesprotector.database.AppDatabase
import com.marcin.jasi.expensesprotector.di.module.ApplicationBindsModule
import com.marcin.jasi.expensesprotector.di.module.ApplicationBindsViewModelsModule
import com.marcin.jasi.expensesprotector.di.module.ApplicationModule
import com.marcin.jasi.expensesprotector.general.domain.helpers.PostExecutionThread
import com.marcin.jasi.expensesprotector.general.domain.helpers.ThreadExecutor
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(
        modules = [
            ApplicationModule::class,
            ApplicationBindsModule::class,
            AndroidInjectionModule::class,
            ApplicationBindsViewModelsModule::class
        ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: Application)

    fun database(): AppDatabase

    fun provideThreadExecutor(): ThreadExecutor

    fun providePostExecutionThread(): PostExecutionThread

}