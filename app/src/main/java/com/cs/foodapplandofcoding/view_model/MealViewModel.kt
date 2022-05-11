package com.cs.foodapplandofcoding.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.model.MealList
import com.cs.foodapplandofcoding.networking.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {

    private var mealDetailLiveData = MutableLiveData<Meal>()
    var isLoading = MutableLiveData<Boolean>(false)

    fun getMealDetail(id: String) {
        isLoading.value = true
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    isLoading.value = false
                    mealDetailLiveData.value = response.body()!!.meals[0]

                } else {
                    isLoading.value = false
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                isLoading.value = false
                Log.d("Error", t.message.toString())
            }

        })
    }

    fun observeMealDetailLiveData(): LiveData<Meal>{
        return mealDetailLiveData

    }

}