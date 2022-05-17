package com.cs.foodapplandofcoding.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs.foodapplandofcoding.db.MealDatabase
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.model.MealList
import com.cs.foodapplandofcoding.networking.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    // viewmodel provider is created for passing the constructor values of viewModel
//    // The internal MutableLiveData that stores the status of the most recent request
//    private val _status = MutableLiveData<Meal>()
//
//    // The external immutable LiveData for the request status
//    val status: LiveData<Meal> = _status

    private var _mealDetailLiveData = MutableLiveData<Meal>()
    // convert mutable live data into live data
    val mealDetailLiveData : LiveData<Meal> = _mealDetailLiveData

    var isLoading = MutableLiveData<Boolean>(false)

    fun getMealDetail(id: String) {
        isLoading.value = true
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    isLoading.value = false
                    _mealDetailLiveData.value = response.body()!!.meals[0]
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
    // mutable live data converted into live data
//    fun observeMealDetailLiveData(): LiveData<Meal>{
//        return _mealDetailLiveData
//    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }



}