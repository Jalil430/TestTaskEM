package com.example.testtaskem.view.ui.dialog

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.domain.model.BasketDishes
import com.example.domain.model.Dishes
import com.example.domain.usecase.basket_usecase.InsertBasketDishesUseCase
import com.example.testtaskem.R
import com.example.testtaskem.databinding.DialogItemDishBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DishDialog(
    private val sharedPreferences: SharedPreferences,
    private val insertBasketDishesUseCase: InsertBasketDishesUseCase,
    private val dish: Dishes
) : DialogFragment() {

    private var _binding: DialogItemDishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogItemDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivDishExit.setOnClickListener {
                dialog?.dismiss()
            }
            Glide
                .with(requireContext())
                .load(dish.imageUrl)
                .fitCenter()
                .into(binding.ivDishImage)

            tvPDishName.text = dish.name
            tvDishPrice.text = "${dish.price} ₽"
            tvDishWeight.text = " - ${dish.weight}г"
            tvDishDescription.text = dish.description

            btnAddToBasket.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    insertBasketDishesUseCase(
                        BasketDishes(
                            dish.id, dish.name, dish.price, dish.weight, dish.imageUrl
                        )
                    )
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_dialog)
    }
}