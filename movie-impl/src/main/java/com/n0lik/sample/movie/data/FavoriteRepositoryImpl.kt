package com.n0lik.sample.movie.data

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.movie.data.db.dao.FavoriteDao
import com.n0lik.sample.movie.data.db.entity.FavoriteEntity
import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FavoriteRepositoryImpl
@Inject constructor(
    private val appDispatcher: AppDispatcher,
    private val dao: FavoriteDao,
) : FavoriteRepository {

    override suspend fun add(movieId: Movie.Id) {
        withContext(appDispatcher.io) {
            dao.insert(FavoriteEntity(movieId.id))
        }
    }

    override suspend fun remove(movieId: Movie.Id) {
        withContext(appDispatcher.io) {
            dao.delete(movieId.id)
        }
    }

    override fun isFavorite(movieId: Movie.Id): Flow<Boolean> {
        return dao.isFavorite(movieId.id)
    }
}