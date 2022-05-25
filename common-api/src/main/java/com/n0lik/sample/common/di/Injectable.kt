package com.n0lik.sample.common.di

import com.n0lik.sample.common.AppDependency

interface Injectable {

    fun inject(dependency: AppDependency)
}