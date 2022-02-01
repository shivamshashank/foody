package com.example.foody.binding_adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foody.R
import com.example.foody.models.food_recipe.Result
import com.example.foody.ui.fragments.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipeRowBinding {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result){
            recipeRowLayout.setOnClickListener{
                try{
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.e("onRecipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(textView: TextView, vegan: Boolean) {
            if(vegan){
                textView.setTextColor(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.green,
                    )
                )
                for (drawable in textView.compoundDrawablesRelative) {
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(
                                ContextCompat.getColor(
                                    textView.context,
                                    R.color.green,
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?) {
            if(description != null){
                textView.text = Jsoup.parse(description).text()
            }
        }

    }

}