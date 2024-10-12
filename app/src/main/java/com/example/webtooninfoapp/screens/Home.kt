package com.example.webtooninfoapp.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.webtooninfoapp.MainViewModel
import com.example.webtooninfoapp.data.models.MangaDTO
import com.example.webtooninfoapp.navigation.Routes
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("WebToon Info App", fontSize = 18.sp) },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        ListContent(navController = navController, modifier = Modifier.padding(it), viewModel = viewModel)
    }
}

@Composable
fun ListContent(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier
) {
    val webtoons = viewModel.pagingData.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(webtoons.itemCount) { index ->
            webtoons[index]?.let {
                WebtoonItem(webtoon = it) {
                    val jsonString = Gson().toJson(it)
                    val encodedJson = Uri.encode(jsonString) // Encode the JSON string
                    navController.navigate("${Routes.Details.route}/$encodedJson")
                }
            }
        }
    }
}

@Composable
fun WebtoonItem(
    webtoon: MangaDTO,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        // Display title above the image
        Text(
            text = webtoon.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp, // Adjust font size as needed
            modifier = Modifier.padding(bottom = 4.dp) // Add spacing below the title
        )

        // Display the image with a larger size
        Image(
            painter = rememberAsyncImagePainter(webtoon.thumb),
            contentDescription = webtoon.title,
            modifier = Modifier
                .fillMaxWidth() // Make the image fill the width
                .height(200.dp) // Set a specific height for the image
                .padding(bottom = 8.dp), // Add spacing below the image
            contentScale = ContentScale.Crop
        )

        // Display summary below the image
        Text(
            text = webtoon.summary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
