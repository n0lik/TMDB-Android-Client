package com.n0lik.sample.genres.di

import com.n0lik.sample.common.AppDependency
import dagger.Component

@Component(
    dependencies = [
        AppDependency::class
    ],
    modules = [
        GenreModule::class
    ]
)
interface GenreComponent : GenreDependency {

    @Component.Factory
    interface Factory {

        fun build(appDependency: AppDependency): GenreComponent
    }
}