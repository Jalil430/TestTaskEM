package com.example.testtaskem.view.ui.item

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.testtaskem.databinding.ItemCategoryTopBarBinding

class CategoryTopBarItem(
    private val sharedPreferences: SharedPreferences,
    private val categoryName: String?,
    private val callback: () -> Unit
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
            tvCategoryName.text = categoryName
            ivExit.setOnClickListener { callback.invoke() }
        }
    }

    private fun initProfilePhotoFragment() {
        val childFragment: Fragment = ProfilePhotoItem(requireContext(), sharedPreferences)
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.ivPhotoContainer.id, childFragment).commit()
    }
}