package com.example.webtooninfoapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.webtooninfoapp.data.models.MangaDTO
import com.example.webtooninfoapp.data.remote.NetworkApi
import kotlinx.coroutines.delay
import javax.inject.Inject

class MyPagingSource @Inject constructor(private val api: NetworkApi) :
    PagingSource<Int, MangaDTO>() {
    override fun getRefreshKey(state: PagingState<Int, MangaDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaDTO> {
        val currentPage = params.key ?: 1
        return try {
            delay(3000)
            val response = api.fetchManga(currentPage).data
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
