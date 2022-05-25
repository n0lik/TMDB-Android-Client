package com.n0lik.sample.movie

import androidx.lifecycle.ViewModel
import androidx.paging.PagingSource
import com.n0lik.sample.common.di.ViewModelKey
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.PopularMovieRepository
import com.n0lik.sample.movie.data.PopularMovieRepositoryImpl
import com.n0lik.sample.movie.data.api.MovieKtorApi
import com.n0lik.sample.movie.data.api.MovieKtorApiImpl
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.mapper.MoviePageListMapper
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.presentation.popular.PopularMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface PopularMovieModule {

    @Binds
    fun provideMoviePageMapper(
        impl: MoviePageListMapper
    ): MapperTo<PagedListDto<MovieDto>, PagingSource.LoadResult<Int, Movie>>

    @Binds
    @IntoMap
    @ViewModelKey(PopularMoviesViewModel::class)
    fun providePopularViewModel(viewModel: PopularMoviesViewModel): ViewModel

    @Binds
    fun providePopularMovieRepository(impl: PopularMovieRepositoryImpl): PopularMovieRepository
}

@Module
internal interface MovieKtorApiModule {

    @Binds
    fun provideMovieKtorApi(impl: MovieKtorApiImpl): MovieKtorApi
}