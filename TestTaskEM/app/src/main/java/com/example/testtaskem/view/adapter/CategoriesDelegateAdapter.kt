package com.example.testtaskem.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.domain.common.Constants.BUNDLE_CATEGORY_NAME
import com.example.domain.model.Categories
import com.example.testtaskem.R
import com.example.testtaskem.databinding.AdapterItemCategoriesBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class CategoriesDelegateAdapter(
    private val context: Context,
    private val view: View
) : ViewBindingDelegateAdapter<
        Categories,
        AdapterItemCategoriesBinding>(AdapterItemCategoriesBinding::inflate) {

    override fun AdapterItemCategoriesBinding.onBind(item: Categories) {
        categoriesTab.setOnClickListener {
            val categoryNameBundle = Bundle()
            categoryNameBundle.putString(BUNDLE_CATEGORY_NAME, item.name)
            view.findNavController().navigate(R.id.action_navigation_home_to_navigation_category, categoryNameBundle)
        }
        tvCategoryName.text = item.name
        Glide
            .with(context)
            .load(item.imageUrl)
            .centerCrop()
            .into(ivCategoryImage)
    }

    override fun isForViewType(item: Any) = item is Categories

    override fun Categories.getItemId(): Any = id!!
}