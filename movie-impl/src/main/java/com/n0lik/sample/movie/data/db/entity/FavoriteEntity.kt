package com.n0lik.sample.movie.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVORITE_TABLE_NAME = "favorites"

@Entity(tableName = FAVORITE_TABLE_NAME)
internal data class FavoriteEntity(@PrimaryKey val id: Int)