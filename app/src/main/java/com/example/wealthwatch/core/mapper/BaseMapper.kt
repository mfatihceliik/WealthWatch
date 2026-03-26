package com.example.wealthwatch.core.mapper

abstract class BaseMapper<I, O> {
    abstract fun map(input: I): O

    operator fun invoke(input: I): O = map(input)

    operator fun invoke(inputs: List<I>?): List<O> {
        return inputs?.map { map(it) } ?: emptyList()
    }

    fun map(inputs: List<I>?): List<O> = invoke(inputs)
}
