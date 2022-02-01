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
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.databinding.RecipeRowLayoutBinding
import com.example.foody.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.example.foody.util.RecipesDiffUtil
import org.jsoup.Jsoup

class FavoritesRecipeAdapter : RecyclerView.Adapter<FavoritesRecipeAdapter.MyViewHolder>() {

    private var favoritesRecipeList = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: RecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.recipeImageView.load(favoritesEntity.result.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            binding.titleTextView.text = favoritesEntity.result.title
            binding.descriptionTextView.text = Jsoup.parse(favoritesEntity.result.summary).text()
            binding.heartTextView.text = favoritesEntity.result.aggregateLikes.toString()
            binding.clockTextView.text = favoritesEntity.result.readyInMinutes.toString()

            if(favoritesEntity.result.vegan){
                binding.leafTextView.setTextColor(
                    ContextCompat.getColor(
                        binding.leafTextView.context,
                        R.color.green,
                    )
                )
                for (drawable in binding.leafTextView.compoundDrawablesRelative) {
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(
                                ContextCompat.getColor(
                                    binding.leafTextView.context,
                                    R.color.green,
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
            }

            binding.rowCardView.setOnClickListener{
                try{
                    val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(favoritesEntity.result)
                    binding.recipeRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.e("onRecipeClickListener", e.toString())
                }
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val binding = RecipeRowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = favoritesRecipeList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return favoritesRecipeList.size
    }

    fun setData(newFavoritesRecipeList: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(favoritesRecipeList, newFavoritesRecipeList)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favoritesRecipeList = newFavoritesRecipeList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}