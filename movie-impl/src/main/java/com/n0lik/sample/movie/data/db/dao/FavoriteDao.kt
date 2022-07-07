package com.n0lik.sample.movie.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.n0lik.sample.movie.data.db.entity.FAVORITE_TABLE_NAME
import com.n0lik.sample.movie.data.db.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FavoriteDao {

    @Query("SELECT COUNT(*) FROM $FAVORITE_TABLE_NAME WHERE id LIKE :movieId")
    fun isFavorite(movieId: Int): Flow<Boolean>

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: FavoriteEntity)

    @Query("DELETE FROM $FAVORITE_TABLE_NAME WHERE id IS :movieId")
    suspend fun delete(movieId: Int)
}