package com.cs.foodapplandofcoding.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs.foodapplandofcoding.model.MealsByCategory
import com.cs.foodapplandofcoding.model.MealsByCategoryList
import com.cs.foodapplandofcoding.networking.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCategoryMealsViewModel() : ViewModel() {
    //2nd page
    private val _mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    val mealsLiveData: LiveData<List<MealsByCategory>> = _mealsLiveData



    fun getMealsByCategory(categoryName: String) {

        RetrofitInstance.api.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.isSuccessful) {
                        _mealsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.e("CategoryMealsViewModel", t.message.toString())
                }
            })
    }
}