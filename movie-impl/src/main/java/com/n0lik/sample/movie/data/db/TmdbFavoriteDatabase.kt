package com.n0lik.sample.movie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.n0lik.sample.movie.data.db.dao.FavoriteDao
import com.n0lik.sample.movie.data.db.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)
internal abstract class TmdbFavoriteDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao
}