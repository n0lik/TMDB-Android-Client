package com.n0lik.sample.movie.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.n0lik.sample.movie.data.db.TmdbFavoriteDatabase
import com.n0lik.sample.movie.data.db.entity.FavoriteEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class FavoriteDaoTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var db: TmdbFavoriteDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun prepare() {
        db = Room.inMemoryDatabaseBuilder(context, TmdbFavoriteDatabase::class.java).build()
        dao = db.getFavoriteDao()
    }

    @After
    fun release() {
        db.close()
    }

    @Test
    fun should_return_correct_state_when_movie_added_to_favorite() = runTest {
        val movieId = 1
        val expected = true

        dao.insert(FavoriteEntity(movieId))
        val actual = dao.isFavorite(movieId).first()

        assertEquals(expected, actual)
    }

    @Test
    fun should_return_correct_state_when_movie_added_NOT_to_favorite() = runTest {
        val movieId = 1
        val expected = false

        val actual = dao.isFavorite(movieId).first()

        assertEquals(expected, actual)
    }

    @Test
    fun should_delete_movie_from_favorite() = runTest {
        val movieId = 1
        val expected = false

        dao.insert(FavoriteEntity(movieId))
        dao.delete(movieId)
        val actual = dao.isFavorite(movieId).first()

        assertEquals(expected, actual)
    }
}