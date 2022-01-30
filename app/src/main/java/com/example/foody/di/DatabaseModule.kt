package com.example.foody.di

import android.content.Context
import androidx.room.Room
import com.example.foody.data.database.RecipeDatabase
import com.example.foody.data.network.FoodRecipesApi
import com.example.foody.util.Constants
import com.example.foody.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipeDatabase::class.java,
        DATABASE_NAME,
    ).build()

    @Singleton
    @Provides
    fun provideDao(recipeDatabase: RecipeDatabase) = recipeDatabase.recipeDao()

}