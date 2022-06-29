package com.n0lik.sample.movie

import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.genres.di.GenreDependency
import com.n0lik.sample.movie.presentation.detail.MovieDetailFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        MovieApiModule::class,
        MovieMapperModule::class,
        MovieDetailModule::class,
        ViewModelFactoryModule::class,
        MovieDetailViewModelModule::class
    ],
    dependencies = [
        AppDependency::class,
        GenreDependency::class
    ]
)
interface MovieDetailComponent {

    fun inject(fragment: MovieDetailFragment)

    @Component.Factory
    interface Factory {

        fun build(
            @BindsInstance movieId: Int,
            appDependency: AppDependency,
            genreDependency: GenreDependency
        ): MovieDetailComponent
    }
}