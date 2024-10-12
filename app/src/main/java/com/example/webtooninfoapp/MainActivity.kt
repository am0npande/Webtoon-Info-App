package com.example.webtooninfoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.webtooninfoapp.navigation.NavGraph
import com.example.webtooninfoapp.ui.theme.WebtoonInfoAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebtoonInfoAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}

