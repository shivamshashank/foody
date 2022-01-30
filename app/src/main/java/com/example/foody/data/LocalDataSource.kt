package com.example.foody.data

import com.example.foody.data.database.RecipeDao
import com.example.foody.data.database.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {

    fun readDatabase(): Flow<List<RecipeEntity>> {
        return recipeDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipeEntity) {
        recipeDao.insertRecipes(recipesEntity)
    }

}
