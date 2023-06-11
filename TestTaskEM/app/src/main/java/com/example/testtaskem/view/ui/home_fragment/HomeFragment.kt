package com.example.testtaskem.view.ui.home_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.domain.usecase.product_usecase.GetCategoriesUseCase
import com.example.testtaskem.R
import com.example.testtaskem.databinding.FragmentHomeBinding
import com.example.testtaskem.databinding.ItemTopLocationBarBinding
import com.example.testtaskem.view.adapter.CategoriesDelegateAdapter
import com.example.testtaskem.view.dialog.PhotoDialog
import com.example.testtaskem.viewmodel.home_fragment.HomeViewModel
import com.example.testtaskem.viewmodel.home_fragment.HomeViewModelFactory
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val sharedPreferences: SharedPreferences
) : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(getCategoriesUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeTopBar()
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.categories().observe(viewLifecycleOwner) { result ->
                    if (!result.isLoading) {
                        progressBar.visibility = View.GONE
                        if (result.error.isNotBlank()) {
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                        } else {
                            val categoriesAdapter = CompositeDelegateAdapter(
                                CategoriesDelegateAdapter(requireContext())
                            )
                            recyclerViewCategories.adapter = categoriesAdapter
                            categoriesAdapter.swapData(result.categories)
                            progressBar.visibility = View.GONE
                        }
                    } else {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initializeTopBar() {
        val topBarBinding = ItemTopLocationBarBinding.bind(
            requireView().findViewById(R.id.item_top_location_bar_root)
        )
        topBarBinding.apply {
            ivPhoto.setOnClickListener {
                showPhotoDialog()
            }
        }
    }

    private fun showPhotoDialog() {
        PhotoDialog(sharedPreferences).show(requireFragmentManager(), "PhotoFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}