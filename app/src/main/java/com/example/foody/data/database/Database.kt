package com.example.foody.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foody.data.database.dao.FavoritesDao
import com.example.foody.data.database.dao.RecipeDao
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun favoritesDao(): FavoritesDao

}