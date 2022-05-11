package com.cs.foodapplandofcoding.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.databinding.FragmentHomeBinding
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.screens.MealActivity
import com.cs.foodapplandofcoding.view_model.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    //    val viewModel: HomeViewModel by viewModels()
    private lateinit var randomMeal: Meal


    companion object {
        const val MEAL_ID = "com.cs.foodapplandofcoding.fragments.idMeal"
        const val MEAL_NAME = "com.cs.foodapplandofcoding.fragments.nameMeal"
        const val MEAL_THUMB = "com.cs.foodapplandofcoding.fragments.thumbMeal"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomMeal()
        observerRandomMeals()

        onRandomMealClick()

    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeals() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,  Observer { meal ->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal

        })
    }
}