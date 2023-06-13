package com.example.testtaskem.view.ui.item

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.domain.common.Constants
import com.example.domain.common.Constants.SHARED_PREF_PROFILE_PHOTO
import com.example.testtaskem.R
import com.example.testtaskem.databinding.ItemProfilePhotoBinding
import com.example.testtaskem.view.ui.dialog.PhotoDialog

class ProfilePhotoItem(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : Fragment() {

    private var _binding: ItemProfilePhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemProfilePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uriAsString = sharedPreferences.getString(SHARED_PREF_PROFILE_PHOTO, "") ?: ""
        if (uriAsString.isNotBlank()) {
            val photoUri = Uri.parse(uriAsString)
            ChangePhotoRepositoryImpl().changePhoto(photoUri)
        } else {
            ChangePhotoRepositoryImpl().changePhoto(R.drawable.ic_user_default_photo)
        }

        binding.ivPhoto.setOnClickListener {
            PhotoDialog(sharedPreferences, ChangePhotoRepositoryImpl()).show(requireFragmentManager(), "PhotoDialog")
        }
    }

    inner class ChangePhotoRepositoryImpl : ChangePhotoRepository {
        override fun changePhoto(photo: Any) {
            Glide
                .with(context)
                .load(photo)
                .centerCrop()
                .into(binding.ivPhoto)
        }
    }
}

interface ChangePhotoRepository {
    fun changePhoto(photo: Any)
}