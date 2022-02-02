package com.example.foody.data

import com.example.foody.data.network.FoodRecipesApi
import com.example.foody.models.food_joke.FoodJoke
import com.example.foody.models.food_recipe.FoodRecipe
import com.example.foody.util.Constants.Companion.API_KEY
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

   suspend fun getRecipes(queries: Map<String, String>) : Response<FoodRecipe> {
       return foodRecipesApi.getRecipes(queries)
   }

    suspend fun searchRecipe(searchQuery: Map<String, String>) : Response<FoodRecipe> {
        return foodRecipesApi.searchRecipe(searchQuery)
    }

    suspend fun getFoodJoke() : Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(API_KEY)
    }

}