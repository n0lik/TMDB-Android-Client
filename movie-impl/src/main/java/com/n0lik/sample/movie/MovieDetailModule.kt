package com.n0lik.sample.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.n0lik.sample.common.di.ViewModelKey
import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.data.MovieRepositoryImpl
import com.n0lik.sample.movie.data.api.MovieApi
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.presentation.detail.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import retrofit2.Retrofit

@Module
internal class MovieDetailModule {

    @Provides
    fun provideMovieRepository(
        dispatcher: AppDispatcher,
        movieApi: MovieApi,
        movieMapper: MapperTo<MovieDto, Movie>
    ): MovieRepository {
        return MovieRepositoryImpl(
            dispatcher = dispatcher,
            movieApi = movieApi,
            movieMapper = movieMapper
        )
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}

@Module
internal class ViewModelFactoryModule {

    @Provides
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }
}

@Module
internal interface MovieDetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    fun provideMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}