package com.example.webtooninfoapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.webtooninfoapp.data.local.WebDao
import com.example.webtooninfoapp.data.models.MangaDTO
import com.example.webtooninfoapp.data.pagination.MyPagingSource
import com.example.webtooninfoapp.data.remote.NetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: NetworkApi,
    private val dao: WebDao,
) {
    fun fetchingMangaList(): Flow<PagingData<MangaDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 2,
                enablePlaceholders = false,
                initialLoadSize = 10,
            ),
            pagingSourceFactory = { MyPagingSource(api) }
        ).flow
    }

    suspend fun upsertWebtoon(webtoon: MangaDTO) {
        dao.upsert(webtoon) // Insert or update a webtoon into the favorites
    }

    suspend fun deleteWebtoon(webtoon: MangaDTO) = dao.delete(webtoon)

    fun getFavoriteWebtoon(): Flow<List<MangaDTO>> {
        return dao.getArticles().onEach { it.reversed() }
    }

    suspend fun updateWebtoon(webtoon: MangaDTO) {
        dao.update(webtoon)
    }

}