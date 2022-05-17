package com.cs.foodapplandofcoding.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs.foodapplandofcoding.db.MealDatabase
import com.cs.foodapplandofcoding.view_model.HomeViewModel
import com.cs.foodapplandofcoding.view_model.MealViewModel

class HomeViewModelFactory(private val mealDatabase: MealDatabase):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }

}