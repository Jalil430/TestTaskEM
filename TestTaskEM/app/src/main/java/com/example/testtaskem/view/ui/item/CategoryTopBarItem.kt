package com.example.testtaskem.view.ui.item

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.example.testtaskem.R
import com.example.testtaskem.databinding.ItemCategoryTopBarBinding

class CategoryTopBarItem(
    private val sharedPreferences: SharedPreferences
) : Fragment() {

    private var _binding: ItemCategoryTopBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemCategoryTopBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfilePhotoFragment()
        binding.apply {
            val categoryName = arguments?.getString("categoryName")
            tvCategoryName.text = categoryName
            ivExit.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_navigation_category_to_navigation_home)
            }
        }
    }

    private fun initProfilePhotoFragment() {
        val childFragment: Fragment = ProfilePhotoItem(sharedPreferences)
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.ivPhotoContainer.id, childFragment).commitAllowingStateLoss()
    }
}