package com.example.webtooninfoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.webtooninfoapp.data.models.MangaDTO
import com.example.webtooninfoapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {



    lateinit var pagingData: Flow<PagingData<MangaDTO>>

    // State for favorites
    private val _favoriteWebtoon = MutableStateFlow(FavoriteState())
    val favoriteWebtoon = _favoriteWebtoon.asStateFlow()

    init {
        fetchManga()  // Fetch manga when the ViewModel is initialized
        getFavoriteWebtoons() // Load favorites when the ViewModel is initialized
    }

    // Fetch paginated manga data from the API
    fun fetchManga() {
        viewModelScope.launch {
            pagingData = repository.fetchingMangaList().cachedIn(viewModelScope)
        }
    }

    fun addWebtoonToFavorites(webtoon: MangaDTO) {
        viewModelScope.launch {
            repository.upsertWebtoon(webtoon)
            getFavoriteWebtoons() // Refresh the favorite list
        }
    }


    fun removeWebtoonFromFavorites(webtoon: MangaDTO) {
        viewModelScope.launch {
            repository.deleteWebtoon(webtoon)
            getFavoriteWebtoons() // Refresh the favorite list
        }
    }


    private fun getFavoriteWebtoons() {
        viewModelScope.launch {
            repository.getFavoriteWebtoon().collect { webtoons ->
                _favoriteWebtoon.value =
                    FavoriteState(webtoons) // Update with the new list of favorites
            }
        }
    }

    fun rateWebtoon(webtoon: MangaDTO, rating: Int) {

        val totalRatings = webtoon.totalRatings + 1
        val newAverage = (webtoon.averageRating * webtoon.totalRatings + rating) / totalRatings

        // Create an updated Webtoon
        val updatedWebtoon = webtoon.copy(averageRating = newAverage, totalRatings = totalRatings)

        // Update the database
        viewModelScope.launch {
            repository.updateWebtoon(updatedWebtoon) // Implement this in your repository
        }
    }
}

// Data class to hold favorite state
data class FavoriteState(
    val webtoons: List<MangaDTO> = emptyList(),
)
