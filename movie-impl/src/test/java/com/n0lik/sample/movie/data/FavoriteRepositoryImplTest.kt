package com.n0lik.sample.movie.data

import com.n0lik.common.test.dispatcher.TestAppDispatcher
import com.n0lik.sample.movie.data.db.dao.FavoriteDao
import com.n0lik.sample.movie.data.db.entity.FavoriteEntity
import com.n0lik.sample.movie.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
internal class FavoriteRepositoryImplTest {

    private val testAppDispatcher = TestAppDispatcher()
    private val mockFavoriteDao = mockk<FavoriteDao>()

    private val repository = FavoriteRepositoryImpl(testAppDispatcher, mockFavoriteDao)

    @Test
    fun `should call insert method when add to favorite`() = runTest {
        coEvery { mockFavoriteDao.insert(FavoriteEntity(1)) } returns Unit

        repository.add(Movie.Id(1))

        coVerify { mockFavoriteDao.insert(FavoriteEntity(1)) }
    }

    @Test
    fun `should call delete method when remove from favorite`() = runTest {
        coEvery { mockFavoriteDao.delete(1) } returns Unit

        repository.remove(Movie.Id(1))

        coVerify { mockFavoriteDao.delete(1) }
    }

    @Test
    fun `should call isFavorite method when check favorite state`() = runTest {
        coEvery { mockFavoriteDao.isFavorite(1) } returns flowOf(true)
        val expected = true

        val actual = repository.isFavorite(Movie.Id(1)).first()

        assertEquals(expected, actual)
    }
}