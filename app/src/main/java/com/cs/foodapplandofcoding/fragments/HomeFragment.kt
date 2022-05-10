package com.cs.foodapplandofcoding.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.R
import com.cs.foodapplandofcoding.databinding.FragmentHomeBinding
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.model.MealList
import com.cs.foodapplandofcoding.networking.RetrofitInstance
import com.cs.foodapplandofcoding.view_model.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomMeal()
        observerRandomMeals()

    }

    private fun observerRandomMeals() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal> {
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgRandomMeal)
            }

        })
    }
}