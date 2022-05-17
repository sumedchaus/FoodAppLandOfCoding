package com.cs.foodapplandofcoding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.databinding.MealItemBinding
import com.cs.foodapplandofcoding.model.Meal

class FavouriteMealsAdapter : RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealsAdapterViewHolder>() {

    inner class FavouriteMealsAdapterViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    // diffUtils is used to increase the  performence of recycler view
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteMealsAdapterViewHolder {
        return FavouriteMealsAdapterViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteMealsAdapterViewHolder, position: Int) {
       val meal =differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

            holder.binding.tvMealName.text = meal.strMeal
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}