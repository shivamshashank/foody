package com.example.foody.ui.fragments.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.R
import com.example.foody.adapters.FavoritesRecipeAdapter
import com.example.foody.databinding.FragmentFavoriteRecipesBinding
import com.example.foody.view_models.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { FavoritesRecipeAdapter(requireActivity(), mainViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        setupRecyclerView()

        readFavoritesRecipes()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_all_favorites_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_favorites_menu) {
            mainViewModel.deleteAllFavoriteRecipe()
            Snackbar.make(
                binding.favoriteRecipesFragment,
                "All Recipes Removed.",
                Snackbar.LENGTH_LONG,
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readFavoritesRecipes() {
        lifecycleScope.launch {
            mainViewModel.readFavoritesRecipes.observe(viewLifecycleOwner) { favoritesEntity ->

                if (favoritesEntity.isNullOrEmpty()) {
                    binding.errorTextView.visibility = View.VISIBLE
                    mAdapter.setData(emptyList())
                } else {
                    binding.errorTextView.visibility = View.GONE
                    mAdapter.setData(favoritesEntity)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        mAdapter.clearContextualActionMode()
    }

}