package com.cs.foodapplandofcoding.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.R
import com.cs.foodapplandofcoding.databinding.ActivityMealBinding
import com.cs.foodapplandofcoding.fragments.HomeFragment

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String

    lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getMealInformationFromIntent()
        setInformationInViews()
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}