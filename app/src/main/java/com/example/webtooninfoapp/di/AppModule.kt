package com.example.webtooninfoapp.di

import android.content.Context
import androidx.room.Room
import com.example.kabar.data.local.WebtoonDataBase
import com.example.webtooninfoapp.data.local.WebDao
import com.example.webtooninfoapp.data.remote.NetworkApi
import com.example.webtooninfoapp.data.repository.Repository
import com.example.webtooninfoapp.utils.Constants.API_HOST
import com.example.webtooninfoapp.utils.Constants.API_KEY_3
import com.example.webtooninfoapp.utils.Constants.BASE_URL
import com.example.webtooninfoapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("x-rapidapi-key", API_KEY_3)  // Replace API_KEY with your actual API key
                    .header("x-rapidapi-host", API_HOST) // Replace API_HOST with your actual API host
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: NetworkApi, dao: WebDao): Repository {
        return Repository(api = api, dao = dao)
    }

    @Singleton
    @Provides
    fun provideDatabase( @ApplicationContext context: Context): WebtoonDataBase {
        return Room.databaseBuilder(
            context,
            WebtoonDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideWebDao(db: WebtoonDataBase): WebDao {
        return db.webDao
    }
}