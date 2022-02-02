package com.example.foody.ui.fragments.joke

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foody.databinding.FragmentFoodJokeBinding
import com.example.foody.util.NetworkResult
import com.example.foody.view_models.MainViewModel

class FoodJokeFragment : Fragment() {

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)

        requestApiData()

        return binding.root
    }

    private fun requestApiData() {
        Log.d("FoodJokeFragment", "requestApiData called!")
        mainViewModel.getFoodJoke()
        mainViewModel.getFoodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeProgressBar.visibility = View.GONE
                    binding.foodJokeCardView.visibility = View.VISIBLE
                    binding.foodJokeTextView.text = response.data?.text
                }
                is NetworkResult.Error -> {
                    binding.foodJokeProgressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.errorTextView.text = response.message

                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_LONG,
                    ).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}