package com.n0lik.sample.common.mapper

interface Mapper0<T, R> {

    fun mapTo(t: T): R

    fun mapToList(models: Iterable<T>): List<R> = models.map { this.mapTo(it) }
}

interface Mapper1<T1, T2, R> {

    fun mapTo(t1: T1, t2: T2): R

    fun mapToList(t1: Iterable<T1>, t2: T2): List<R> = t1.map { this.mapTo(it, t2) }
}