package com.example.webtooninfoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.webtooninfoapp.MainViewModel
import com.example.webtooninfoapp.data.models.MangaDTO


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(
    navController: NavHostController,
    webtoon: MangaDTO,
    viewModel: MainViewModel = hiltViewModel()
) {
    var isFavorite by remember { mutableStateOf(webtoon.favourite) }
    var userRating by remember { mutableStateOf(0) } // State to store user's rating

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        val updatedWebtoon = webtoon.copy(favourite = isFavorite)
                        if (isFavorite) {
                            viewModel.addWebtoonToFavorites(updatedWebtoon)
                        } else {
                            viewModel.removeWebtoonFromFavorites(updatedWebtoon)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberAsyncImagePainter(webtoon.thumb),
                contentDescription = webtoon.title,
                modifier = Modifier
                    .size(250.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = webtoon.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = webtoon.summary,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display average rating
            Text(
                text = "Average Rating: ${webtoon.averageRating} (${webtoon.totalRatings} ratings)",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Rating buttons
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                for (i in 1..5) {
                    IconButton(onClick = {
                        userRating = i
                        viewModel.rateWebtoon(webtoon, i) // Call rating method
                    }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "$i Star",
                            modifier = Modifier.size(24.dp),
                            tint = if (userRating >= i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
