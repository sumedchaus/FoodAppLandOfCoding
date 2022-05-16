package com.cs.foodapplandofcoding.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cs.foodapplandofcoding.adapters.CategoryMealsAdapter
import com.cs.foodapplandofcoding.databinding.ActivityCategoryMealsBinding
import com.cs.foodapplandofcoding.fragments.HomeFragment
import com.cs.foodapplandofcoding.view_model.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var viewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter : CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareecyclerView()
        viewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
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

    private fun prepareecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeal.apply {
            layoutManager = GridLayoutManager(
                context, 2,GridLayoutManager.VERTICAL, false)

            adapter = categoryMealsAdapter
        }
    }
}