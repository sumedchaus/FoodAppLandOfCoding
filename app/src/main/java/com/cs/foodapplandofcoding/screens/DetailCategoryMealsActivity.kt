package com.cs.foodapplandofcoding.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cs.foodapplandofcoding.adapters.DetailCategoryMealsAdapter
import com.cs.foodapplandofcoding.databinding.ActivityDetailCategoryMealsBinding
import com.cs.foodapplandofcoding.fragments.HomeFragment
import com.cs.foodapplandofcoding.view_model.DetailCategoryMealsViewModel

class DetailCategoryMealsActivity : AppCompatActivity() {
    //  2nd page

    lateinit var binding: ActivityDetailCategoryMealsBinding
    lateinit var viewModel: DetailCategoryMealsViewModel
    lateinit var categoryMealsAdapter : DetailCategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        detailCategoryListClick()
        viewModel = ViewModelProvider(this)[DetailCategoryMealsViewModel::class.java]
        // to call from button Click of category
        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        viewModel.mealsLiveData.observe(this, Observer { mealsList ->

            binding.tvCategoryCount.text = mealsList.size.toString()
            mealsList.forEach {
                Log.d("test", it.strMeal)
            }

            categoryMealsAdapter.setMealsList(mealsList)

        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = DetailCategoryMealsAdapter()
        binding.rvMeal.apply {
            layoutManager = GridLayoutManager(
                context, 2,GridLayoutManager.VERTICAL, false)

            adapter = categoryMealsAdapter
        }
    }


    private fun detailCategoryListClick() {
        categoryMealsAdapter.onItemClick = { meal ->
        val intent = Intent(this, DetailMealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }
}