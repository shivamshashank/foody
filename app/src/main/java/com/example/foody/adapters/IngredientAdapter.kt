package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.databinding.IngredientsRowLayoutBinding
import com.example.foody.models.food_recipe.ExtendedIngredient
import com.example.foody.util.Constants
import com.example.foody.util.RecipesDiffUtil

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            IngredientsRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]

        holder.binding.ingredientImageView.load(Constants.BASE_IMAGE_URL + currentIngredient.image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.ingredientName.text = currentIngredient.name
        holder.binding.ingredientAmount.text = currentIngredient.amount.toString()
        holder.binding.ingredientUnit.text = currentIngredient.unit
        holder.binding.ingredientConsistency.text = currentIngredient.consistency
        holder.binding.ingredientOriginal.text = currentIngredient.original
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