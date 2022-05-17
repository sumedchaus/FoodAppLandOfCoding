package com.cs.foodapplandofcoding.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.R
import com.cs.foodapplandofcoding.databinding.ActivityDetailMealBinding
import com.cs.foodapplandofcoding.db.MealDatabase
import com.cs.foodapplandofcoding.fragments.HomeFragment
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.view_model.DetailMealViewModel
import com.cs.foodapplandofcoding.viewmodel_factory.MealViewModelFactory

class DetailMealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var viewModel: DetailMealViewModel
    private lateinit var youtubeLink: String

    private lateinit var binding: ActivityDetailMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
         viewModel = ViewModelProvider(this,viewModelFactory)[DetailMealViewModel::class.java]

        loadingCase()
        getMealInformationFromIntent()
        setInformationInViews()

        viewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
        onFavouriteClick()

    }

    private fun onFavouriteClick() {

        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                viewModel.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave : Meal? = null
    private fun observeMealDetailsLiveData() {
//        viewModel.observeMealDetailLiveData().observe(this, Observer { meal ->
        viewModel.mealDetailLiveData.observe(this, Observer { meal ->

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
            mealToSave = meal
            binding.tvCategory.text = "Category: ${meal.strCategory} "
            binding.tvArea.text = "Area: ${meal.strArea} "
            binding.instructionDetails.text = "Area: ${meal.strInstructions} "
            youtubeLink = meal.strYoutube!!
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