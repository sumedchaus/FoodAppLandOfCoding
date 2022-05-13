package com.cs.foodapplandofcoding.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs.foodapplandofcoding.model.CategoryList
import com.cs.foodapplandofcoding.model.CategoryMeals
import com.cs.foodapplandofcoding.model.Meal
import com.cs.foodapplandofcoding.model.MealList
import com.cs.foodapplandofcoding.networking.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private  var randomMealLiveData =  MutableLiveData<Meal>()
    private  var popularItemsLiveData =  MutableLiveData<List<CategoryMeals>>()


    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(
            object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                    if(response.isSuccessful){
                        val result  = response.body()
                        val randomMeal = result!!.meals[0]
                        randomMealLiveData.value = randomMeal

                        Log.d("TestMeal", "id: ${randomMeal.idMeal}, name:${randomMeal.strMeal} ")
                    } else{
                        return
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("Error Home Fragment" , t.message.toString())
                }

            })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               if(response.isSuccessful){
                   popularItemsLiveData.value = response.body()!!.meals
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Error Home Fragment" , t.message.toString())
            }

        })
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }
}