package com.example.testtaskem.view.ui.category_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.domain.common.Constants
import com.example.domain.model.Dishes
import com.example.domain.usecase.basket_usecase.InsertBasketDishesUseCase
import com.example.domain.usecase.product_usecase.GetDishesUseCase
import com.example.testtaskem.R
import com.example.testtaskem.databinding.FragmentCategoryBinding
import com.example.testtaskem.view.adapter.DishesDelegateAdapter
import com.example.testtaskem.view.ui.item.CategoryTopBarItem
import com.example.testtaskem.viewmodel.category_fragment.CategoryViewModel
import com.example.testtaskem.viewmodel.category_fragment.CategoryViewModelFactory
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment(
    private val getDishesUseCase: GetDishesUseCase,
    private val insertBasketDishesUseCase: InsertBasketDishesUseCase,
    private val sharedPreferences: SharedPreferences
    ) : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(getDishesUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(requireView(), 100f)

        initTopBarFragment()

        val dishesAdapter = CompositeDelegateAdapter(
            DishesDelegateAdapter(
                requireContext(),
                sharedPreferences,
                insertBasketDishesUseCase,
                requireFragmentManager()
            )
        )

        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.dishes().observe(viewLifecycleOwner) { result ->
                    if (!result.isLoading) {
                        progressBar2.visibility = View.GONE

                        if (result.error.isNotBlank()) {
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                        } else {
                            val dishes = result.dishes
                            initRecyclerView(dishes, dishesAdapter)

                            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                                when (checkedId) {
                                    chipWholeMenu.id -> {
                                        dishesAdapterSwapData(dishes, dishesAdapter)
                                    }
                                    chipSalads.id -> {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val sortedDishes = dishes.applySort(chipSalads.text.toString())
                                            dishesAdapterSwapData(sortedDishes, dishesAdapter)
                                        }
                                    }
                                    chipWithRice.id -> {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val sortedDishes = dishes.applySort(chipWithRice.text.toString())
                                            dishesAdapterSwapData(sortedDishes, dishesAdapter)
                                        }
                                    }
                                    chipWithFish.id -> {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val sortedDishes = dishes.applySort(chipWithFish.text.toString())
                                            dishesAdapterSwapData(sortedDishes, dishesAdapter)
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        progressBar2.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initTopBarFragment() {
        val categoryName = requireArguments().getString(Constants.BUNDLE_CATEGORY_NAME)
        val args = Bundle()
        args.putString("categoryName", categoryName)
        val childFragment: Fragment = CategoryTopBarItem(sharedPreferences)
        childFragment.arguments = args
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.topBarCategoryFragmentContainer.id, childFragment).commit()
    }

    private fun initRecyclerView(dishes: List<Dishes>, dishesAdapter: CompositeDelegateAdapter) {
        binding.apply {
            recyclerViewDishes.adapter = dishesAdapter
            dishesAdapterSwapData(dishes, dishesAdapter)
            progressBar2.visibility = View.GONE
        }
    }

    private fun dishesAdapterSwapData(dishes: List<Dishes>, dishesAdapter: CompositeDelegateAdapter) {
        dishesAdapter.swapData(dishes)
    }

    private suspend fun List<Dishes>.applySort(teg: String): List<Dishes> {
        val dishes = this
        val sortedDishes = CoroutineScope(Dispatchers.IO).async {
            val list = mutableListOf<Dishes>()
            for (a in dishes.indices) {
                for (b in dishes[a].tegs!!.indices) {
                    if (teg == dishes[a].tegs!![b]) {
                        list.add(dishes[a])
                    }
                }
            }
            return@async list
        }.await()

        return sortedDishes
    }

    private fun goBack() {
        requireView().findNavController().navigate(R.id.action_navigation_category_to_navigation_home)
    }
}