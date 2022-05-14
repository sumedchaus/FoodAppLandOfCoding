package com.cs.foodapplandofcoding.networking

import com.cs.foodapplandofcoding.model.CategoryList
import com.cs.foodapplandofcoding.model.MealsByCategoryList
import com.cs.foodapplandofcoding.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("api/json/v1/1/random.php")
    fun getRandomMeal(): Call<MealList>


    @GET("api/json/v1/1/lookup.php?")
    fun getMealDetails(
        @Query("i")
        id: String
    ): Call<MealList>

    @GET("api/json/v1/1/filter.php?")
    fun getPopularItems(
        @Query("c")
        categoryName: String
    ): Call<MealsByCategoryList>

    @GET("api/json/v1/1/categories.php?")
    fun getCategory(
    ): Call<CategoryList>

}