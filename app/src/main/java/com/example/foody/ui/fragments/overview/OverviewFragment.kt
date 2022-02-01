package com.example.foody.ui.fragments.overview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.foody.R.color.green
import com.example.foody.databinding.FragmentOverviewBinding
import com.example.foody.models.food_recipe.Result
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val recipeBundle: Result? = args?.getParcelable("recipeBundle")

        binding.imageView.load(recipeBundle?.image)
        binding.likeTextView.text = recipeBundle?.aggregateLikes.toString()
        binding.timeTextView.text = recipeBundle?.readyInMinutes.toString()
        binding.titleTextView.text = recipeBundle?.title
        if (recipeBundle != null) {
            binding.summaryTextView.text = Jsoup.parse(recipeBundle.summary).text()
        }

        if (recipeBundle?.vegan == true) {
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), green))
            binding.veganTextView.compoundDrawables.getOrNull(0)?.setTint(Color.GREEN)
        }

        if (recipeBundle?.dairyFree == true) {
            binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), green))
            binding.dairyFreeTextView.compoundDrawables.getOrNull(0)?.setTint(Color.GREEN)
        }

        if (recipeBundle?.veryHealthy == true) {
            binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), green))
            binding.healthyTextView.compoundDrawables.getOrNull(0)?.setTint(Color.GREEN)
        }

        if (recipeBundle?.vegetarian == true) {
            binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), green))
            binding.vegetarianTextView.compoundDrawables.getOrNull(0)?.setTint(Color.GREEN)
        }

        if (recipeBundle?.glutenFree == true) {
            binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), green))
            binding.glutenFreeTextView.compoundDrawables.getOrNull(0)?.setTint(Color.GREEN)
        }

        if (recipeBundle?.cheap == true) {
            binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), green))
            binding.cheapTextView.compoundDrawables.getOrNull(0)?.setTint(Color.GREEN)
        }

        return binding.root
    }

}