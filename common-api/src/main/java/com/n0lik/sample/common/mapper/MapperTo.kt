package com.n0lik.sample.common.mapper

interface MapperTo<I, O> {

    fun mapTo(model: I): O

    fun mapToList(models: Iterable<I>): List<O> = models.map { model -> this.mapTo(model) }
}