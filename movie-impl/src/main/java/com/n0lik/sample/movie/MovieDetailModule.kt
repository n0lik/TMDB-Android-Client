package com.n0lik.sample.movie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.n0lik.sample.common.di.ViewModelKey
import com.n0lik.sample.movie.data.FavoriteRepository
import com.n0lik.sample.movie.data.FavoriteRepositoryImpl
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.data.MovieRepositoryImpl
import com.n0lik.sample.movie.data.api.MovieApi
import com.n0lik.sample.movie.data.db.TmdbFavoriteDatabase
import com.n0lik.sample.movie.data.db.dao.FavoriteDao
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
    fun provideFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    fun provideMovieDetailInteractor(impl: MovieDetailInteractorImpl): MovieDetailInteractor
}

@Module
internal class FavoriteDbModule {

    @Provides
    fun provideFavoriteDb(context: Context): TmdbFavoriteDatabase {
        return Room.databaseBuilder(
            context,
            TmdbFavoriteDatabase::class.java,
            FAVORITE_DB_NAME
        ).build()
    }

    @Provides
    fun provideFavorite(database: TmdbFavoriteDatabase): FavoriteDao {
        return database.getFavoriteDao()
    }

    companion object {

        private const val FAVORITE_DB_NAME = "favorite_db"
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