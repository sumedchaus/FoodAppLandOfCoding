package com.cs.foodapplandofcoding.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs.foodapplandofcoding.db.MealDatabase
import com.cs.foodapplandofcoding.model.*
import com.cs.foodapplandofcoding.networking.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoryItemsLiveData = MutableLiveData<List<Category>>()
    private var _favouritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    val favouritesMealsLiveData : LiveData<List<Meal>> = _favouritesMealsLiveData
    private var _bottomSheetMealLiveData = MutableLiveData<Meal>()
    var bottomSheetMealLiveData : LiveData<Meal> = _bottomSheetMealLiveData


    fun getRandomMeal() {

        RetrofitInstance.api.getRandomMeal().enqueue(
            object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                    if (response.isSuccessful) {
                        val result = response.body()
                        val randomMeal = result!!.meals[0]
                        randomMealLiveData.value = randomMeal

                        Log.d("TestMeal", "id: ${randomMeal.idMeal}, name:${randomMeal.strMeal} ")
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("Error Home Fragment", t.message.toString())
                }

            })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.isSuccessful) {
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("Error Home Fragment", t.message.toString())
                }

            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.isSuccessful) {
                    categoryItemsLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Error Home Fragment", t.message.toString())
            }


        })
    }

    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal.let {
                    _bottomSheetMealLiveData.postValue(it)
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Error Home Fragment", t.message.toString())
            }

        })

    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }

    fun deleteMeal(meal:Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoryItemsLiveData

    }
}