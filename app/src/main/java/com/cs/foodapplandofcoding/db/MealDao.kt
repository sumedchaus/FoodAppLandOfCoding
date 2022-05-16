package com.cs.foodapplandofcoding.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cs.foodapplandofcoding.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)


    // or can use on conflixt mwthod
    @Update
    suspend fun updateMeal(meal:Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}