package com.cs.foodapplandofcoding.networking

import com.cs.foodapplandofcoding.model.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("api/json/v1/1/random.php")
    fun getRandomMeal() : Call<MealList>
}