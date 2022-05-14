package com.cs.foodapplandofcoding.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.R
import com.cs.foodapplandofcoding.databinding.ActivityMealBinding
import com.cs.foodapplandofcoding.fragments.HomeFragment
import com.cs.foodapplandofcoding.view_model.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var viewModel: MealViewModel
    private lateinit var youtubeLink: String

    private lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MealViewModel::class.java]

        loadingCase()
        getMealInformationFromIntent()
        setInformationInViews()

        viewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        viewModel.observeMealDetailLiveData().observe(this, Observer { meal ->

//             use one of the method
//            viewModel.isLoading.observe(this, Observer {

//                if (it == true) {
//                    binding.progressBar.visibility = View.VISIBLE
//                    binding.btnAddToFav.visibility = View.INVISIBLE
//                    binding.tvInstruction.visibility = View.INVISIBLE
//                    binding.tvCategory.visibility = View.INVISIBLE
//                    binding.tvArea.visibility = View.INVISIBLE
//                    binding.imgYoutube.visibility = View.INVISIBLE
//
//                } else {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    binding.btnAddToFav.visibility = View.VISIBLE
//                    binding.tvInstruction.visibility = View.VISIBLE
//                    binding.tvCategory.visibility = View.VISIBLE
//                    binding.tvArea.visibility = View.VISIBLE
//                    binding.imgYoutube.visibility = View.VISIBLE
//                    binding.tvCategory.text = "Category: ${meal.strCategory} "
//                    binding.tvArea.text = "Area: ${meal.strArea} "
//                    binding.instructionDetails.text = "Area: ${meal.strInstructions} "
//                }
//            })


            onResponseCase()
            binding.tvCategory.text = "Category: ${meal.strCategory} "
            binding.tvArea.text = "Area: ${meal.strArea} "
            binding.instructionDetails.text = "Area: ${meal.strInstructions} "
            youtubeLink = meal.strYoutube
        })
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


    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}