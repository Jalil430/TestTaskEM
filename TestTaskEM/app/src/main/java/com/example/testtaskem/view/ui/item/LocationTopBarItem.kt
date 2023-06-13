package com.example.testtaskem.view.ui.item

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.domain.common.Constants.SHARED_PREF_LOCATION
import com.example.testtaskem.databinding.ItemLocationTopBarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class LocationTopBarItem(
    private val sharedPreferences: SharedPreferences
) : Fragment() {

    private var _binding: ItemLocationTopBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemLocationTopBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfilePhotoFragment()

        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val cityName = flow {
                    while (true) {
                        val city = sharedPreferences.getString(SHARED_PREF_LOCATION, "") ?: ""
                        if (city != "") {
                            emit(city)
                            break
                        } else {
                            delay(500)
                        }
                    }
                }

                cityName.collect { city ->
                    tvLocation.text = city
                }
            }
            tvDate.text = getCurrentDate()
        }
    }

    private fun initProfilePhotoFragment() {
        val childFragment: Fragment = ProfilePhotoItem(requireContext(), sharedPreferences)
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.ivPhotoContainer.id, childFragment).commit()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(Date())
    }
}