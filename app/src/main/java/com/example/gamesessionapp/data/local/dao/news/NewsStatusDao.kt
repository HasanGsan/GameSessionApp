package com.example.gamesessionapp.data.local.dao.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamesessionapp.data.local.entity.favorite.FavoriteEntity
import com.example.gamesessionapp.data.local.entity.favorite.ReadPostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsStatusDao {

    @Query("SELECT * FROM favorites_posts")
    fun getAllFavorites() : Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites_posts WHERE newsId = :newsId")
    fun removeFavorite(newsId: String)

    @Query("SELECT COUNT(*) FROM favorites_posts WHERE newsId = :newsId")
    fun isFavorite(newsId: String) : Int

    @Query("SELECT * FROM read_posts")
    fun getAllReadPosts() : Flow<List<ReadPostEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun addReadPost(readPost: ReadPostEntity)

    @Query("DELETE FROM read_posts WHERE newsId = :newsId")
    fun removeReadPost(newsId: String)

    @Query("SELECT COUNT(*) FROM read_posts WHERE newsId = :newsId")
    fun isRead(newsId: String) : Int

}