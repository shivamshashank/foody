package com.example.foody.data

import com.example.foody.data.database.dao.FavoritesDao
import com.example.foody.data.database.dao.RecipeDao
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
    private val favoritesDao: FavoritesDao,
) {

    fun readRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipeEntity) {
        recipeDao.insertRecipes(recipesEntity)
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return favoritesDao.readFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        favoritesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        favoritesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        favoritesDao.deleteAllFavoriteRecipes()
    }

}
