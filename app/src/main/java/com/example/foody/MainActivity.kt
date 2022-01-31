package com.example.foody

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.foody.databinding.ActivityMainBinding
import com.example.foody.fragments.favorite.FavoriteRecipesFragment
import com.example.foody.fragments.joke.FoodJokeFragment
import com.example.foody.fragments.recipes.RecipesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(RecipesFragment())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.recipesFragment->setCurrentFragment(RecipesFragment())
                R.id.favoriteRecipesFragment->setCurrentFragment(FavoriteRecipesFragment())
                R.id.foodJokeFragment->setCurrentFragment(FoodJokeFragment())
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragment)
        commit()
    }

}