package com.example.testtaskem.view.ui.basket_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.domain.usecase.basket_usecase.DeleteBasketDishesUseCase
import com.example.domain.usecase.basket_usecase.GetBasketDishesUseCase
import com.example.testtaskem.databinding.FragmentBasketBinding
import com.example.testtaskem.view.adapter.BasketItemsDelegateAdapter
import com.example.testtaskem.view.ui.item.LocationTopBarItem
import com.example.testtaskem.viewmodel.basket_fragment.BasketViewModel
import com.example.testtaskem.viewmodel.basket_fragment.BasketViewModelFactory
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BasketFragment(
    private val getBasketDishesUseCase: GetBasketDishesUseCase,
    private val deleteBasketDishesUseCase: DeleteBasketDishesUseCase,
    private val sharedPreferences: SharedPreferences
) : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BasketViewModel by viewModels() {
        BasketViewModelFactory(getBasketDishesUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopBarFragment()
        initRecyclerView()

        viewModel.price.observe(viewLifecycleOwner) { price ->
            if (price == null || price <= 0) {
                binding.btnPay.text = "Добавьте блюда в корзину"
            } else {
                binding.btnPay.text = "Оплатить $price ₽"
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.dishes().observe(viewLifecycleOwner) { dishes ->
                    val basketItemsAdapter = CompositeDelegateAdapter(
                        BasketItemsDelegateAdapter(
                            requireContext(),
                            deleteBasketDishesUseCase
                        ) { plusPrice: Int?, minusPrice: Int? ->
                            CoroutineScope(Dispatchers.Main).launch {
                                viewModel.calculatePrices(plusPrice, minusPrice)
                            }
                        }
                    )
                    recyclerViewBasketDishes.adapter = basketItemsAdapter
                    basketItemsAdapter.swapData(dishes)
                }
            }
        }
    }

    private fun initTopBarFragment() {
        val childFragment: Fragment = LocationTopBarItem(sharedPreferences)
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.topBarLocationFragmentContainer2.id, childFragment).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}