package com.n0lik.sample.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.n0lik.sample.common.di.ViewModelKey
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.data.MovieRepositoryImpl
import com.n0lik.sample.movie.data.api.MovieApi
import com.n0lik.sample.movie.domain.MovieDetailInteractor
import com.n0lik.sample.movie.domain.MovieDetailInteractorImpl
import com.n0lik.sample.movie.presentation.detail.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Provider

@Module
internal class MovieApiModule {

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}

@Module
internal interface MovieDetailModule {

    @Binds
    fun provideMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun provideMovieDetailInteractor(impl: MovieDetailInteractorImpl): MovieDetailInteractor
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