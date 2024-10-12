package com.example.webtooninfoapp.data.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MangaDTO(
    @PrimaryKey
    val id: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val total_chapter: Int,
    val nsfw: Boolean,
    val type: String,
    val status: String,
    val favourite: Boolean = false,
    val averageRating: Float = 0f,
    val totalRatings: Int = 0
) : Parcelable
