package com.marcin.jasi.expensesprotector.general.data.common

interface DataMapper<From, To> {

    fun transform(from: From): To

    fun transform(from: List<From>): List<To> {
        val list: MutableList<To> = mutableListOf()

        return list
                .apply {
                    from.forEach { add(transform(it)) }
                }
    }

}