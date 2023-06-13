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
import com.example.domain.usecase.product_usecase.GetDishesUseCase
import com.example.testtaskem.R
import com.example.testtaskem.databinding.FragmentCategoryBinding
import com.example.testtaskem.view.adapter.CategoriesDelegateAdapter
import com.example.testtaskem.view.adapter.DishesDelegateAdapter
import com.example.testtaskem.view.ui.item.CategoryTopBarItem
import com.example.testtaskem.viewmodel.category_fragment.CategoryViewModel
import com.example.testtaskem.viewmodel.category_fragment.CategoryViewModelFactory
import com.example.testtaskem.viewmodel.home_fragment.HomeViewModel
import com.example.testtaskem.viewmodel.home_fragment.HomeViewModelFactory
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment(
    private val getDishesUseCase: GetDishesUseCase,
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
    }

    private fun initTopBarFragment() {
        val categoryName = requireArguments().getString(Constants.BUNDLE_CATEGORY_NAME)
        val childFragment: Fragment = CategoryTopBarItem(sharedPreferences, categoryName) { //onExitBtnPressed ->
            requireView().findNavController().navigate(R.id.action_navigation_category_to_navigation_home)
        }
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.topBarCategoryFragmentContainer.id, childFragment).commit()
    }

    private fun initRecyclerViews() {
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.dishes().observe(viewLifecycleOwner) { result ->
                    if (!result.isLoading) {
                        progressBar.visibility = View.GONE
                        if (result.error.isNotBlank()) {
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                        } else {
                            val categoriesAdapter = CompositeDelegateAdapter(
                                DishesDelegateAdapter(requireContext(), requireView())
                            )
                            recyclerViewDishes.adapter = categoriesAdapter
                            categoriesAdapter.swapData(result.dishes)
                            progressBar.visibility = View.GONE
                        }
                    } else {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}