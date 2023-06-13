package com.example.testtaskem.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.domain.common.Constants
import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import com.example.testtaskem.R
import com.example.testtaskem.databinding.AdapterItemCategoriesBinding
import com.example.testtaskem.databinding.AdapterItemDishesBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class DishesDelegateAdapter(
    private val context: Context,
    private val view: View
) : ViewBindingDelegateAdapter<
        Dishes,
        AdapterItemDishesBinding>(AdapterItemDishesBinding::inflate){

    override fun AdapterItemDishesBinding.onBind(item: Dishes) {



        Glide
            .with(context)
            .load(item.imageUrl)
            .centerCrop()
            .into(ivDishImage)
        tvDishName.text = item.name




//        categoriesTab.setOnClickListener {
//            val categoryNameBundle = Bundle()
//            categoryNameBundle.putString(Constants.BUNDLE_CATEGORY_NAME, item.name)
//            view.findNavController().navigate(R.id.action_navigation_home_to_navigation_category, categoryNameBundle)
//        }
//        tvCategoryName.text = item.name
//        Glide
//            .with(context)
//            .load(item.imageUrl)
//            .centerCrop()
//            .into(ivCategoryImage)
    }

    override fun isForViewType(item: Any) = item is Dishes

    override fun Dishes.getItemId(): Any = id!!
}