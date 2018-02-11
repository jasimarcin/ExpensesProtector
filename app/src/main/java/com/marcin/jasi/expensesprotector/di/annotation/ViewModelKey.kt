package com.marcin.jasi.expensesprotector.di.annotation

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.annotation.Target
import kotlin.reflect.KClass

@Documented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
