package com.n0lik.sample.movie

import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.genres.di.GenreDependency
import com.n0lik.sample.movie.presentation.popular.PopularMoviesFragment
import dagger.Component

@Component(
    modules = [
        MovieKtorApiModule::class,
        ViewModelFactoryModule::class,
        PopularMovieModule::class,
        MovieMapperModule::class
    ],
    dependencies = [
        AppDependency::class,
        GenreDependency::class
    ]
)

internal interface PopularMovieComponent : PopularMovieDependency {

    @Component.Factory
    interface Factory {

        fun build(
            dependency: AppDependency,
            genreDependency: GenreDependency
        ): PopularMovieComponent
    }

    fun inject(fragment: PopularMoviesFragment)
}