package com.example.foody.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.databinding.RecipeRowLayoutBinding
import com.example.foody.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.example.foody.util.RecipesDiffUtil
import com.example.foody.view_models.MainViewModel
import com.google.android.material.snackbar.Snackbar
import org.jsoup.Jsoup

class FavoritesRecipeAdapter(
    private val fragmentActivity: FragmentActivity,
    private val mainViewModel: MainViewModel,
) : RecyclerView.Adapter<FavoritesRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var favoritesRecipeList = emptyList<FavoritesEntity>()

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
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentFavoriteRecipeResult = favoritesRecipeList[position].result

        holder.binding.recipeImageView.load(currentFavoriteRecipeResult.image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.titleTextView.text = currentFavoriteRecipeResult.title
        holder.binding.descriptionTextView.text =
            Jsoup.parse(currentFavoriteRecipeResult.summary).text()
        holder.binding.heartTextView.text = currentFavoriteRecipeResult.aggregateLikes.toString()
        holder.binding.clockTextView.text = currentFavoriteRecipeResult.readyInMinutes.toString()

        if (currentFavoriteRecipeResult.vegan) {
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

        // Single Click Listener
        holder.binding.recipeRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, favoritesRecipeList[position])
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        currentFavoriteRecipeResult
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        // Long Click Listener
        holder.binding.recipeRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                fragmentActivity.startActionMode(this)
                applySelection(holder, favoritesRecipeList[position])
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return favoritesRecipeList.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)

        mActionMode = mode!!
        changeStatusBarColor(R.color.contextualStatusBarColor)

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.favorites_contextual_delete_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            Snackbar.make(
                rootView,
                "${selectedRecipes.size} Recipe/s removed.",
                Snackbar.LENGTH_SHORT
            ).show()

            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true

    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        changeStatusBarColor(R.color.statusBarColor)
    }

    fun setData(newFavoritesRecipeList: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(favoritesRecipeList, newFavoritesRecipeList)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favoritesRecipeList = newFavoritesRecipeList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun changeStatusBarColor(color: Int) {
        fragmentActivity.window.statusBarColor = ContextCompat.getColor(fragmentActivity, color)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.recipeRowLayout.setBackgroundColor(
            ContextCompat.getColor(fragmentActivity, backgroundColor)
        )
        holder.binding.rowCardView.strokeColor =
            ContextCompat.getColor(fragmentActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

}