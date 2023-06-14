package com.example.testtaskem.view.adapter

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.domain.model.Dishes
import com.example.domain.usecase.basket_usecase.InsertBasketDishesUseCase
import com.example.testtaskem.databinding.AdapterItemDishesBinding
import com.example.testtaskem.view.ui.dialog.DishDialog
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class DishesDelegateAdapter(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val insertBasketDishesUseCase: InsertBasketDishesUseCase,
    private val fragmentManager: FragmentManager
) : ViewBindingDelegateAdapter<
        Dishes,
        AdapterItemDishesBinding>(AdapterItemDishesBinding::inflate) {

    override fun AdapterItemDishesBinding.onBind(item: Dishes) {
        val dishDialog = DishDialog(sharedPreferences, insertBasketDishesUseCase, item)

        rootDish.setOnClickListener {
            dishDialog.show(fragmentManager, "DishDialog")
        }

        tvDishName.text = item.name
        Glide
            .with(context)
            .load(item.imageUrl)
            .fitCenter()
            .into(ivDishImage)
    }

    override fun isForViewType(item: Any) = item is Dishes

    override fun Dishes.getItemId(): Any = id!!
}