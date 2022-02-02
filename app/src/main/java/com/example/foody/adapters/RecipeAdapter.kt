package com.example.foody.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.databinding.RecipeRowLayoutBinding
import com.example.foody.models.food_recipe.FoodRecipe
import com.example.foody.models.food_recipe.Result
import com.example.foody.ui.fragments.recipes.RecipesFragmentDirections
import com.example.foody.util.RecipesDiffUtil
import org.jsoup.Jsoup

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private var recipesList = emptyList<Result>()

    class MyViewHolder(val binding: RecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecipeRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipesList[position]

        holder.binding.recipeImageView.load(currentRecipe.image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.titleTextView.text = currentRecipe.title
        holder.binding.descriptionTextView.text = Jsoup.parse(currentRecipe.summary).text()
        holder.binding.heartTextView.text = currentRecipe.aggregateLikes.toString()
        holder.binding.clockTextView.text = currentRecipe.readyInMinutes.toString()

        if (currentRecipe.vegan) {
            holder.binding.leafTextView.setTextColor(
                ContextCompat.getColor(
                    holder.binding.leafTextView.context,
                    R.color.green,
                )
            )
            for (drawable in holder.binding.leafTextView.compoundDrawablesRelative) {
                if (drawable != null) {
                    drawable.colorFilter =
                        PorterDuffColorFilter(
                            ContextCompat.getColor(
                                holder.binding.leafTextView.context,
                                R.color.green,
                            ),
                            PorterDuff.Mode.SRC_IN
                        )
                }
            }
        }

        holder.binding.recipeRowLayout.setOnClickListener {
            try {
                val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(
                    currentRecipe
                )
                holder.binding.recipeRowLayout.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.e("onRecipeClickListener", e.toString())
            }
        }

    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    fun setData(newRecipesList: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipesList, newRecipesList.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipesList = newRecipesList.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}