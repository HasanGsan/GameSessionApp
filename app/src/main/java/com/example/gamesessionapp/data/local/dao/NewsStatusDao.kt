package com.example.gamesessionapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamesessionapp.data.local.entity.FavoriteEntity
import com.example.gamesessionapp.data.local.entity.ReadPostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsStatusDao {

    @Query("SELECT * FROM favorites_posts")
    fun getAllFavorites() : Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites_posts WHERE newsId = :newsId")
    suspend fun removeFavorite(newsId: String)

    @Query("SELECT COUNT(*) FROM favorites_posts WHERE newsId = :newsId")
    suspend fun isFavorite(newsId: String) : Int

    @Query("SELECT * FROM read_posts")
    fun getAllReadPosts() : Flow<List<ReadPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReadPost(readPost: ReadPostEntity)

    @Query("DELETE FROM read_posts WHERE newsId = :newsId")
    suspend fun removeReadPost(newsId: String)

    @Query("SELECT COUNT(*) FROM read_posts WHERE newsId = :newsId")
    suspend fun isRead(newsId: String) : Int

}