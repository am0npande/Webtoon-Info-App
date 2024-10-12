package com.example.kabar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.webtooninfoapp.data.local.WebDao
import com.example.webtooninfoapp.data.models.MangaDTO


@Database(entities = [MangaDTO::class], version = 1)

abstract class WebtoonDataBase: RoomDatabase() {
    abstract val webDao: WebDao
}

//it is abstract because room will implement it for us