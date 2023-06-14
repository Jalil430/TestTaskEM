package com.example.testtaskem.view.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.example.domain.model.BasketDishes
import com.example.domain.usecase.basket_usecase.DeleteBasketDishesUseCase
import com.example.testtaskem.databinding.AdapterItemBasketDishesBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketItemsDelegateAdapter(
    private val context: Context,
    private val deleteBasketDishesUseCase: DeleteBasketDishesUseCase,
    private val callback: (Int?, Int?) -> Unit
) : ViewBindingDelegateAdapter<
        BasketDishes,
        AdapterItemBasketDishesBinding>(AdapterItemBasketDishesBinding::inflate) {

    override fun AdapterItemBasketDishesBinding.onBind(item: BasketDishes) {
        var amount = 1
        ivPlus.setOnClickListener {
            amount++
            tvAmount.text = amount.toString()
            callback(item.price!!, null)
        }
        ivMinus.setOnClickListener {
            amount--
            if (amount <= 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    callback(null, item.price!!)
                    deleteBasketDishesUseCase(item.id!!)
                }
            } else {
                tvAmount.text = amount.toString()
                callback(null, item.price!!)
            }
        }

        callback(item.price!!, null)
        tvAmount.text = amount.toString()
        tvDishName2.text = item.name
        tvDishPrice2.text = "${item.price} ₽"
        tvDishWeight2.text = " - ${item.weight}г"
        Glide
            .with(context)
            .load(item.imageUrl)
            .fitCenter()
            .into(ivDishImage2)
    }

    override fun isForViewType(item: Any) = item is BasketDishes

    override fun BasketDishes.getItemId(): Any = id!!
}