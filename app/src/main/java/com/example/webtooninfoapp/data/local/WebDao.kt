package com.example.webtooninfoapp.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.webtooninfoapp.data.models.MangaDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface WebDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //update the article if conflicting data
    suspend fun upsert(article: MangaDTO)

    @Delete
    suspend fun delete(article: MangaDTO)

    @Query("SELECT * FROM MangaDTO")
    fun getArticles():Flow<List<MangaDTO>>

    @Update
    suspend fun update(webtoon: MangaDTO)
}
