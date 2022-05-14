package com.cs.foodapplandofcoding.view_model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cs.foodapplandofcoding.databinding.CategoryItemBinding
import com.cs.foodapplandofcoding.databinding.PopularItemsBinding
import com.cs.foodapplandofcoding.model.Category

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoryList =  ArrayList<Category>()

    fun setCategoryList(categoriesList: List<Category>){
        categoryList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
       return CategoriesViewHolder(
           CategoryItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
    inner class CategoriesViewHolder( val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


}