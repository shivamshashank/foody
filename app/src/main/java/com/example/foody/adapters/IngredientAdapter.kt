package com.example.foody.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.databinding.IngredientsRowLayoutBinding
import com.example.foody.models.food_recipe.ExtendedIngredient
import com.example.foody.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foody.util.RecipesDiffUtil

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentIngredient: ExtendedIngredient) {
            binding.ingredientImageView.load(BASE_IMAGE_URL + currentIngredient.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            binding.ingredientName.text = currentIngredient.name
            binding.ingredientAmount.text = currentIngredient.amount.toString()
            binding.ingredientUnit.text = currentIngredient.unit
            binding.ingredientConsistency.text = currentIngredient.consistency
            binding.ingredientOriginal.text = currentIngredient.original
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val binding = IngredientsRowLayoutBinding.inflate(
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
        val currentIngredient = ingredientsList[position]
        Log.e("ABCD", BASE_IMAGE_URL + currentIngredient.image)

        holder.bind(currentIngredient)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

}