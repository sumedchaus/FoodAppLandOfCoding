package com.cs.foodapplandofcoding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cs.foodapplandofcoding.MainActivity
import com.cs.foodapplandofcoding.adapters.FavouriteMealsAdapter
import com.cs.foodapplandofcoding.databinding.FragmentFavouritesBinding
import com.cs.foodapplandofcoding.view_model.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouriteAdapter: FavouriteMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecycleView()
        observeFavourites()

        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favouriteAdapter.differ.currentList[position])

            Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                "Undo"
            ) {
                viewModel.insertMeal(favouriteAdapter.differ.currentList[position -1])
            }.show()
            }


        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun prepareRecycleView() {

        favouriteAdapter = FavouriteMealsAdapter()
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favouriteAdapter

        }

    }

    private fun observeFavourites() {
        viewModel.favouritesMealsLiveData.observe(viewLifecycleOwner, Observer { meals ->

            favouriteAdapter.differ.submitList(meals)
        })
    }


}