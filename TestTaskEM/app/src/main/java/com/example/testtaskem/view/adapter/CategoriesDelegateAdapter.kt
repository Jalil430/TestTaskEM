package com.example.testtaskem.view.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.example.domain.model.Categories
import com.example.testtaskem.databinding.AdapterItemCategoriesBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class CategoriesDelegateAdapter(private val context: Context) : ViewBindingDelegateAdapter<
        Categories,
        AdapterItemCategoriesBinding>(AdapterItemCategoriesBinding::inflate) {

    override fun AdapterItemCategoriesBinding.onBind(item: Categories) {
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