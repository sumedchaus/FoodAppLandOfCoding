package com.cs.foodapplandofcoding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.MainActivity
import com.cs.foodapplandofcoding.adapters.MostPopularMealAdapter
import com.cs.foodapplandofcoding.databinding.FragmentHomeBinding
import com.cs.foodapplandofcoding.fragments.bottomsheet.MealBottomSheetFragment
import com.cs.foodapplandofcoding.model.MealsByCategory
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.screens.DetailCategoryMealsActivity
import com.cs.foodapplandofcoding.screens.DetailMealActivity
import com.cs.foodapplandofcoding.adapters.CategoriesAdapter
import com.cs.foodapplandofcoding.view_model.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    //    val viewModel: HomeViewModel by viewModels()
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularMealAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object {
        const val MEAL_ID = "com.cs.foodapplandofcoding.fragments.idMeal"
        const val MEAL_NAME = "com.cs.foodapplandofcoding.fragments.nameMeal"
        const val MEAL_THUMB = "com.cs.foodapplandofcoding.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.cs.foodapplandofcoding.fragments.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // no need to create multiple instance of viewmodel just create one in the Activity of given fragments
//        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel = (activity as MainActivity).viewModel


        popularItemAdapter = MostPopularMealAdapter()
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


        preparePopularItemsRecycleView()

        viewModel.getRandomMeal()
        observerRandomMeals()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()

        omPopularItemLongClick()

    }

    private fun omPopularItemLongClick() {
        popularItemAdapter.onLongItemClick ={ meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, DetailCategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer{ categories ->
              categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity, DetailMealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecycleView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = popularItemAdapter
        }
    }

    private fun observePopularItemLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner, Observer { mealList ->

            popularItemAdapter.setList(mealList = mealList as ArrayList<MealsByCategory>)
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, DetailMealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeals() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        })
    }
}